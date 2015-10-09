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
 * csvFilename:  full path to the csv filename.
 * Shiro is used to protect access to all REST resources.  The password given in shiro.ini is a hashed equivalent of the plain text one set in the partner's payments setup.  The steps include:
   1. Set the password in your partner's paymentOptions resource.  Contact EvenUp for details.
   2. Use Shiro's "shiro-tools-hasher-cli" found at [http://shiro.apache.org/download.html](http://shiro.apache.org/download.html).  Calling as follows:
java -jar shiro-tools-hasher-1.2.3-cli.jar -a SHA-256 <password>
   3. Paste the result into the password field in the shiro.ini file
 * keyFilePath: if specified (and it should be), the processor will look for an AES key with which to encrypt the data prior to writing.  
   1. The key file can be generated using the jar produced in this project.:  java -cp evenup-payment-processor-1.0.1-SNAPSHOT-jar-with-dependencies.jar com.evenup.payment.processor.crypto.AesKeyManager keyfile.aes
   2. The full path to the file is then referenced in keyFilePath.
 
##Running
The payment processor is run:
java -jar evenup-payment-processor-1.0.1-SNAPSHOT-jar-with-dependencies.jar server <path to config file>

