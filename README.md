#Payments Processor

##Overview
This is a simple service that receives payment requests from EvenUp and writes them to an encrypted CSV file.

###Data
The payments data for a credit card has the following elements:

* requestedPaymentDate
* remitAmount
* accountId
* accountNumber
* creditCardInfo
  * accountHolderName
  * number
  * type
  * expirationMonth
  * expirationYear
  * securityCode

###Encryption
If configured, payment information will be encrypted before being written to the file, using the AES key provided in the keyFilePath below.

##Configuration
Payments Processor is a [Dropwizard](http://www.dropwizard.io/) application, using a single YAML file for configuration with the following elements:

 * server: contains settings for applicationConnectors and adminConnectors.  Set the ports to listen on here.  This must match the port given in the paymentOptions setup (contact EvenUp for details).
 * csvMapping: this maps the column header names in the CSV file to the fields of the incoming JSON.  The fields in the JSON can be retrieved by specify their names and using a dot notation for nested fields, e.g. "creditCardInfo.type".
 * fileDestination: the directory that the csvFilename should be created in.
 * csvFilename:  The csv filename to create in fileDestination.  This should contain a variable for the datetime ${date}, e.g. myPaymentsFile-${date}.csv
 * decryptedFileDestination: The directory in which to put the decrypted version of the csv file when the new-file task is
 invoked.
 * Shiro is used to protect access to all REST resources.  The password given in shiro.ini is a hashed equivalent of the plain text one set in the partner's payments setup.  The steps include:
   1. Set the password in your partner's paymentOptions resource.  Contact EvenUp for details.
   2. Use Shiro's "shiro-tools-hasher-cli" found at [http://shiro.apache.org/download.html](http://shiro.apache.org/download.html).  Calling as follows:
java -jar shiro-tools-hasher-1.2.3-cli.jar -a SHA-256 <password>
   3. Paste the result into the password field in the shiro.ini file
 * keyFilePath: if specified (and it should be), the processor will look for an AES key with which to encrypt the data prior to writing.  
   1. The key file can be generated using the jar produced in this project.:  java -cp evenup-payment-processor-1.0.1-SNAPSHOT-jar-with-dependencies.jar com.evenup.payment.processor.crypto.AesKeyManager keyfile.aes
   2. The full path to the file is then referenced in keyFilePath.
 
##Running
Prior to running a key file must be created if you intend to write this as an encrypted file.  This is done using a utility in the payment processor jar:

java -cp evenup-payment-processor-1.0.1-jar-with-dependencies.jar com.evenup.payment.processor.crypto.AesKeyManager my-key-file.txt

This resulting key file is passed to the processor in the keyFilePath field in the yaml file described above, e.g. keyFilePath: my-key-file.txt
The payment processor is then run:

java -jar evenup-payment-processor-1.0.1-jar-with-dependencies.jar server <path to config file>

When you are ready to cut a new file, you should invoke the "new-file" task.  This is accomplished by send a POST request (with no body) to the admin port specified above.  If you have access to cUrl, this would look like:

curl -XPOST  http://127.0.0.1:8091/tasks/new-file
where 8091 was specified as the admin port.

If the process must be stopped prior to decrypting, the following command can be used to manually decrypt the file:

java -cp evenup-payment-processor-1.0.1-jar-with-dependencies.jar com.evenup.payment.processor.crypto.AesDecryptFileUtil my-key-file.txt  <path to csv file given in csvFilename in the yaml file> <path to output file>

