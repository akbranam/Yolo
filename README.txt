Anna Branam

YOLO: a bucket list app.

This app was built for and tested on a Nexus 5 with Android version marshmallow 6.0.1.

The first fragment is the home screen of the app, displaying the app’s title and four buttons:
	Try something: when pressed, this button displays a random item from the to-do list
	New: when pressed, this button takes the user to the new item screen
	About: this button takes the user to the about pop-up screen containing information about the app
	Exit: this button exits the app

The second fragment contains a ListView which holds the app’s uncompleted items.

The third fragment contains a ListView which holds the app’s completed items.

The new item screen allows the user to specify an item’s name, the date by which it should be completed, the location at which the item should be completed and the item’s relative difficulty. However, only the title and the difficulty are necessary to specify, the app will generate default “whenever” and “wherever” values for the other two fields.
The items of the app’s two lists are selectable and, when pressed, send the user to the view item screen. This screen is populated with the item’s information. From this screen, the user has the option to edit the item, delete it, complete it if the item hasn’t been completed already and exit the screen. I

If the edit item button is pressed, the user is taken to the edit item screen. This screen is much like the new item screen, however, its fields come prepopulated with the information from the view item screen. Once edits to this item are complete, the user may accept or discard changes, with both actions returning the user to the view item screen

The information for the items in the lists is stored in a single SQLite database. Each item has an auto-generated id which the user is never privy to. This id is used in both updating and deleting items.

Finally, there is the settings screen, accessed by selecting the settings feature from the app’s toolbar. There are two settings options:
	Sounds: the user can choose to toggle sounds on or off. There are four events that trigger sounds: a new item, a completed item, a changed item, and a deleted item.
	Numbered lists: This feature adds numbers to both lists. These numbers relate to the item’s position in each list, not its database id.

The four sound files used in this app were royalty free and found at www.soundjay.com