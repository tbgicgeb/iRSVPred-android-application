# iRSVPred 1.0

<div align="center">
  <img src="https://apexbtic.icgeb.res.in/rice/ss_1.jpeg" height="200px">
</div>

<br>
In agriculture as well as trade, seed variety is pivotal for determining crop yield, price, and quality. However, identification of the variety either requires a laboratory test or being relied on a experienced person both of which is time consuming and costly.

Addressing this gap, we present iRSVPred, a groundbreaking Android application harnessing machine learning based on seed images. Developed to cater to the needs of paddy farmers, iRSVPred offers an alternative, easily accessible, and rapid method for identifying ten major varieties of basmati rice. These include Pusa basmati 1121, Pusa basmati 1509, Pusa basmati 1637, CSR 30 (salt-tolerant basmati), DHBT-3, PB-1, PB-6, BAS-370, 1718, and 1728.

With iRSVPred, farmers can seamlessly upload seed images directly from their camera, eliminating the need for costly and time-consuming molecular techniques. This user-friendly solution empowers farmers to make informed decisions about their crops, ultimately enhancing agricultural productivity and quality. Experience the future of paddy seed identification with iRSVPred – bridging the gap between cutting-edge technology and practical farming needs.

## Requirements

The project works well with Android Studio 4.0.0 and Gradle 6.1.1.\
Java was used for the backend and XML for frontend.\
Mininum SDK 17, thereby supporting Android 4.2 and above.\

PhotoView:2.3.0 – to provide zoom in and zoom out feature\
Volley:1.1.1 – to send user data\
Youtube Android Player API – to play the YouTube tutorial video (Obsolete) \
Jsoup:1.12.1 – for data extraction

## Build
1. Install Android studio 4.0.0
2. Install the required SDK and libraries.
3. sync
4. Replace the commented URL section in ServerConnectionActivity.java and ContactUsActivity.java and add your custom URLs.


## Android Developer
Android application is developed by [Hemant Kumar Joon](https://www.linkedin.com/in/hemantjoon/)\
To know more about the Machine learning model, please visit the [Repo](https://github.com/arunsharma8osdd/iRSVPred).\
Visit our [Webserver](https://apexbtic.icgeb.res.in/rice)

## License

[Apache License 2.0](LICENSE)
