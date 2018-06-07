# TRUNK Smart Reconcile (Spring/Backend)
We have used for [Spring Tool Suite](https://spring.io/tools/sts/all) for our development.
## Steps to setup local development environment
1. Clone the repository using the following command in any directory (Eg: /home/ABC/Workspaces/STS/)
```console
git clone https://github.com/ishwarvirdi/TrunkSpring.git
```
2. Open the STS workspace and select **File >> Import**.
3. Under General, select *Projects from Folder or Archive*.
4. For Import Source, click Directory and select the TrunkSpring folder created from the git clone (/home/ABC/Workspaces/STS/TrunkSpring).
5. Click Finish.
6. Wait for Maven to install all packages. Once complete, your local IDE setup is ready.
## If you want to Run Locally and Not Heroku & AWS
1. Open the **application.properties** file under **src/main/resources**.
2. Comment the below line which points to the Heroku frontend,
```
frontend.endpoint=https://trunksmartreconcilereact.herokuapp.com
```
3. Uncomment the below line which points to the local system frontend,
```
frontend.endpoint=http://localhost:3000
```
4. Comment the below line which points to the AWS MongoDB instance,
```
spring.data.mongodb.uri=mongodb://13.211.38.88:27017/trunk
```
5. Uncomment the below line which points to the local system MongoDB instance,
```
spring.data.mongodb.uri=mongodb://localhost:27017/trunkSpring
```
6. Modify Redis setting under **#redis** to point to the local Redis instance,
7. Modify AWS S3 setting under **#aws s3** to point to your AWS S3 bucket,
## System Configurations
1. Upto how many months of data needs to be shown in the Dashboard,
```
dashboard.limit=5
```
2. Upto how many months back does the reconcilation work for,
```
reconcileAlgo.limit=3
```
