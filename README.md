# HMAC-based One Time Password generator with code delivery via NDEF format on JavaCard 

JavaCard HMAC-based One Time Password generator which delivers new code via URL tag of NDEF every time the card is put close to NFC-enabled phone. 

As a result, phone will display prompt to visit URL with current OTP code. No installation of phone software is required. The server side needs to parse OTP code properly from URL request.  

## Initialization

> More detailed instructions with screenshots are available on the project wiki.

### Setting URL

Using nfc tag writing application (like [this one](https://play.google.com/store/apps/details?id=com.wakdev.wdnfc&hl=cs)) write URL to which you would like to be redirected , for example

    http://hotp.zelitomas.cf/?key=

Code will be appended to the URL, like this:

    http://hotp.zelitomas.cf/?key=1825523596
  
Check the address by tapping your card to NFC-enabled phone (simple counter is used to generate codes until you set the secret)

### Setting HOTP secret

The card accepts otpauth URLs in this format:

    otpauth://hotp/username@server/?secret=base32encodedsecret

(Google Authenticator HOTP URL without counter parameter)

Write with your favorite NFC tag writing application. Counter is always set to 0 when new secret is set.

## Credits

The project is based on:
1. [NDEF applet](https://github.com/promovicz/javacard-ndef) by I. Albrech (NDEF applet)
