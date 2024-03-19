# ImageShareProj
# Intro
This is an two-part application, which makes it possible to share files via links, as well as limit the number of available downloads and the file storage period (for all files)
# Stack
The following frameworks were used during the project:
- Spring 
  - Beans, Shedulling tasks
  - Data: to interact with database entities
- Hibernate: for working with ORM and hql queries
- ModelMapper: working with DTO and some another cases
- Kafka: to communicate between two parts of application (with reply)
- S3 API: to communicate with R2 container (Cloudflare). Update, PreSignedUrl, BatchDelete
JQuery for testing, Docker to start Kafka service
Plans: write IP tracking or cookies system (the ability to delete a file by the owner before the time limit or attempts expire)
# About
It is a simple file sharing application using CloudFlare R2 storage system.
The application allows you to limit the number of downloads by the owner of the downloaded file. If you specify the number of downloads -1, the file can be downloaded an unlimited number of times.
The application also has a scheduled task that requests a list of files that have been stored for more than the app.expiredays period or have 0 available download attempts (not available for download). 
Such files are deleted from both sides of the application (that is, both from the DB and from the storage).
First part of app controll r2 container, second part - db and donwload count tracking.

Usage:
1) In application.properties change spring.datasource with ur database properties
2) Here also change cloud.* variables. U can create r2 api key here (see picture). Region should be auto
   ![image](https://github.com/Laytin/imageShareProj/assets/70861524/f10c0fc4-cca7-4503-b045-ce09db3f06cc)

3) app.domain - in my case localhost:8080 (example)
4) Make sure, spring.kafka.bootstrap-servers have correct port
   To start Kafka in docker, there is simple command "docker run -p 9092:9092 apache/kafka:3.7.0". Change with ur actual port. 
