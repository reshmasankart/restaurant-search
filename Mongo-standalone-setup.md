
### ⚙️ MongoDB Setup
Ensure MongoDB is installed and running. You can use the official MongoDB documentation for installation instructions based on your operating system.

If you have MongoDB installed, ensure it is running. You can check the status with:
```bash 
mongod --version
```
If MongoDB is not installed, you can download it from the [official MongoDB website](https://www.mongodb.com/try/download/community) and follow the installation instructions for your operating system.

If you are using macOS and prefer to use Homebrew, you can install MongoDB with the following commands:

```bash
brew tap mongodb/brew
brew install mongodb-community
```
Start MongoDB service
```bash   
brew services start mongodb/brew/mongodb-community
```
Verify MongoDB is running
 ```bash 
 brew services list
 ```
To Import your sample JSON data (adjust path to your restaurants db):

```bash
mongoimport --db restaurantdb --collection restaurants --file restaurants.json --jsonArray
```
Verify data by starting the MongoDB shell:
```bash
mongosh
> use restaurantdb
> db.restaurants.find().pretty()
> db.restaurants.countDocuments()
```