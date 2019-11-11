# aorms-seda
Android Based Restaurant Management System

An Android based Restaurant Order Management System (AROMS) that allows customers of the restaurants to place order using the restaurant provided tablets. This system will reduce the need of interaction between the customers and the waiters in the restaurant at the time of placing and modifying an order. 

The project consists of the following high level modules which each have their own functional decompositions. The modules interact and depend on each other as well.

1. Order Placement Module
2. Kitchen Management Module
3. Hall Management Module
4. Specialized Order Module
5. Admin and Inventory Management
6. Reporting Module

The following default values are used to determine the project settings:

1. Target SDK Version:     29
2. Minimum SDK Version:    24
3. Gradle Build Version:   3.5.1
4. Google Services:        4.3.2



Color values are to be set as:
1. name="colorPrimary">#182027
2. name="colorPrimaryDark">#293542
3. name="colorAccent">#59FF00
4. name="Background">#F3EEEE
5. name="TextColor">#302E2E
6. name="ButtonTextColor">#F3EEEE
7. name="ButtonColor">#00D4B4
8. name="ActionBarColor">#182027
    
These files and values are also included in the project.

***GUIDELINES***

1. ALWAYS and ONLY push in your own module branch. DO NOT PUSH IN THE MASTER BRANCH :)
2. Kindly add a description of 2-3 during each commit and push so it is easy to identify the difference between the pushes.
2. Use camelCase to name the variables, Object IDs and database fields.
3. When naming Objects in activities kindly use the following format for ease of use and understanding: <ActivityName_objectType#>
4. Generic Names for the frequently objects can be as following:
    1.   Button = btn
    2.   RecyclerView = rcv
    3.   TextView = txtview
    4.   Containers = ctn
    5.   Fragment = frag
    6.   ImageView = Img
5. Maintain a list of the dependicies that you are using in your projects independantly as well so we can match and update them during final integration testings.
6. We are using Firestore (not to be confused with firebase) due to its superior technology and cooler name.

:)

    
