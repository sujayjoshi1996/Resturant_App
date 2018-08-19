# Resturant_App
This is an online client/server android resturant application. In this application customers can order online and come pick up the food within half an hour. 
As th user opens the application, he is prompted to enter the name and then proceed to order page where the user can place his order and then user is displayed with the total including the tax amount, the user needs to submit to place the order.
On the server side their is an inventory file where the quantity of each item avaible and it is reset to 50 quantity of each item from the menu every hour. so based on the availbility of the items the kitchen thread process each order. if the items are not available the user is notified that it is not.
if the items are availabe the order goes to prepation which is random value between 180 to 300 seconds, then packing takes random value between 20 to 30 seconds
Then the user is notifed with the message that the order is ready to be picked up
