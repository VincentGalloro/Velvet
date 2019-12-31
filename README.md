
![alt text](Velvet_Logo.png)  
### Developed By: Vincent Galloro
Velvet is a Java / Kotlin Library created for faster application development.

Velvet is designed to save time when developing visual desktop applications. It is a constantly growing collection of utilities, data structures, helper objects, and UI code. 

Velvet's core package creates a window with an update and render loop, as well as keyboard and mouse input. Velvet's UI package provides "VisualContainers" which can hold text, shapes, and images. These containers can be configured with custom transitions, smooth motion, and animation. These containers are setup to handle all forms of mouse/keyboard interaction. Velvet also contains Versioned readers/writers, which allow for the creation of custom serializers/deserializers as well as automatic saving/loading and file backup. 

All of these tools allow the developer to jump straight into the logic of their app, without worrying about setting up all the usual visual components, or re-writing data structures and helper functions.


## Packages
Velvet contains the following packages:  

### Main 
* For setting up a window with an update and render loop
* Contains a keyboard and mouse object, and a basic graphics object

### VElements 
For all UI related objects
* Basic visual elements for text, image, or shape content. 
* VContainers, which store the visual elements, and travel smoothly around the screen
* Interaction handlers for mouse and keyboard input

### Multithreading
* Bot object. Can be given a list of jobs, which it will execute asynchronously
* Support for bot pools, which operate on a single job queue 

### IO
* Readers and Writers for writing custom objects to and from files, or other data-streams
* Versioning support for migrating older save files to newer program versions
* Rolling backup support for recovering to backups in-case of data loss or crashes

### Web 
For simple web-scraping
* Contains template classes for scraping data from the web
* Classes for navigating a webpage, as well as interacting with buttons / forms

### Structs 
Data structures and utility objects
* A Vector and Position class for storing 2D coordinates with built in arithmetic functions
* Data structures such as the Disjoint Set, 'Auto Set', and 'Shadow Map'
* Time related data objects

## Still to come...
Velvet is still being developed.

Demos and Documentation coming soon!
