# HMAC-based One Time Password generator with code delivery via NDEF format 

The goal is to create the implementation of HMAC-based One Time Passwords generator which will deliver new code via URL tag of NDEF every time the card is put close to NFC-enabled phone. 

As a result, phone will display prompt to visit URL with current OTP code. No installation of phone software is required. The server side needs to parse OTP code properly from URL request.  

The project will be based on:
1. [NDEF applet](https://github.com/promovicz/javacard-ndef) by I. Albrech (NDEF applet)
2. [OATH App for the YubiKey NEO](https://github.com/Yubico/ykneo-oath) by Yubico (HOTP computation)
