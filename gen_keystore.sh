JKS=keystore.jks
CERT=public_cert.pem

openssl req -newkey rsa:2048 -sha256 -nodes \
    -keyout private.key -x509 \
    -days 365 \
    -out $CERT \
    -subj "/C=UA/ST=Kyiv/L=Location/O=RpgMasterAssistantBot/CN=$HOST_IP"

openssl pkcs12 -export \
    -in $CERT \
    -inkey private.key \
    -certfile $CERT \
    -out keystore.p12

keytool -importkeystore \
    -srckeystore keystore.p12 \
    -srcstoretype pkcs12 \
    -sigalg SHA1withRSA \
    -destkeystore $JKS \
    -deststoretype pkcs12

rm keystore.p12 private.key
