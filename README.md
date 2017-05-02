# DatabaseHelper
This project will handle threading issue for database queries in your project and make smooth application. As we know database queries should be run in backThread, this library with create one thread for database queries to run those in back thread will handle this, with this library you can serialize your queries and prevent freezing UI while querying to database. 

Another reason that you might interest to use this library is Priority, you can set priority for your queries. sometimes you have many queries in second but need to execute one of theme earlier than other in the queue, you can easily set priority to your tasks.

this library will guaranty to serialize queries to prevent write/read multiple query.

How To Use?
----

For using this library you need two simple things:

1- start database thread:

    new DatabaseThread().start();

2- execute your query:

    DatabaseManager.getInstance().execute(new Executor<Void>() {
            @Override
            public Void doInDatabaseThread() {
                Log.d(TAG, "do whatEver you want in database thread");
                return null;
            }
        });
        
**DON'T start Thread more than one**, you can use Application class to make sure you start it once.

**Advance usage:**
---

You can start `DatabaseThread` with following arguments:

1- `ErrorHandler`: if your query has a problem and fail to execute this interface will get your Exception, if you don't set it you don't see any error in log or other place. ***Your application Will not crash if you set or not***, this good for debugging and reporting.

2- `Initializer`: if you want do any pre process in database thread you can use this, something like create instance of database or anyThing else.

In `DatabaseManager` you can execute your query with following arguments:

1- `TaskPriority`: is a priority of your executor, `IMMEDIATE` is highest and `LOW` is lowest priority, default value is `NORMAL`

2- `Executor`: is a interface that has 2 method, doInDatabaseThread and onResponseExecute.
`doInDatabaseThread` is method that you must write your query in database thread, this method will call in database thread, so **DON'T** do heavy work like processing or http query in this thread, this thread is responsible for getting data from your database, if you have any post process on your data use `onResponseExecute`
`onResponseExecute` is a method that will execute after `doInDatabaseThread` in back or UI thread based on your argument for `responseOnBackThread`. you can get your data and do whatEver you want.

3- `responseOnBackThread`: is declare `onResponseExecute` run in back thread or UI thread, **default is false** so if you have heavy process on loaded data pass True for this argument to prevent freeze application.


You can specify return value from `doInDatabaseThread` in `Executor` argument like:

    new Executer<String>  

This line make `doInDatabaseThread` retrun `String` object and `onResponseExecute` will get that. (like AsyncTask class )



Contribution
-----

Comments, contributions and patches are greatly appreciated.

License
-----
The MIT License (MIT).
