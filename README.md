# ExpiryDateTracker

### Download the App [Here](https://play.google.com/store/apps/details?id=com.lumibao.expirydatetracker)

### Summary
This app was inspired when I was living alone for the first time and had to cook for myself. I bought myself chicken and when I was going to cook it, it was to my disappointment to find out it was expired. During my first internship I had the pleasure of working with Android Studio and XCode, so for this project I decided to create an Android App. Since there were already so many expiry date tracker in the play store, I wanted to mine simple and easy to use.

The main features of this app includes
1. Free software, no ads are displayed in the app.
2. Easy and intuitive UI, allowing most to pick it up and use it.
3. Notifications, allowing users to use their food before it expires.

### Android Technical Features
1. RecyclerView: Having a RecyclerView allowed for custom lists to be created and allow gestures to be implemented. Swipe to delete was created using the feature and the items in the lists are customized to show relevant information to the user.
2. Persistent Data: Persistent data was achieved by storing the user's information in a JSON file. The JSON file is stored in the app's shared preferences.
3. MVC (Model View Controller): The Model View Controller architecture was followed to create a clean code source.
4. BroadcastReceiver: The BroadcastReceiver was used to implement the notification features of the application. It checks the user's items at a certain time of the day and provides a push notification to the user if the item was expiring soon.

### Application Photos
![simple ui 1](https://user-images.githubusercontent.com/31720028/51189761-b1ed3600-18ae-11e9-9ba6-bf86c93b3840.png) ![free and easy 1](https://user-images.githubusercontent.com/31720028/51189920-160ffa00-18af-11e9-933f-92e68868ba1f.png) ![copy of free and easy 1](https://user-images.githubusercontent.com/31720028/51189940-23c57f80-18af-11e9-8a6b-d627f98972b2.png)

