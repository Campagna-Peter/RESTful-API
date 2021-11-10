# RESTful-API
Distributed Systems - RESTful API with KVS Storage

This project began by learning about the fundamentals of a RESTful API, i.e. the client server model, being stateless, cache,
uniform interaface, layered system, and code on demand. The client server model being the common architecture where we build an
API for a client server model. I then learned about what it means to be stateless. If the server is stateless then the client must
send all the necessary information because a server will not save any client info for later use. The state being referred to here
is session state which is important because server state is allowed to be changed and saves info from the client. For example, server
state is altered through a POST. Next up is caching which is abslutely vital. With caching I explored the idea of idempotency - where
after the first call, exact copies of that same call produce the same result. Therefore, idempotency has to do with server state. I then
explored the uniform interface which include all the HTTP methods GET, PUT, PATCH, POST, DELETE, etc. These, obviosuly became essential 
for developing my API. My team and I had to take into account each of the methods and facts about them. In other words we had to see if 
GET was idempotent and if it has a response body then what should be sent back in the response body. I will delve into specifics of the project
shortly. I then learned the importance of a layered system in that we don't want the client to know what server we are communicating with. 
If a client knows the server they are communicating with then they can become reliant on that server to perform a specific way. This can
create a lot of issues when we make changes to the server. Lastly we talked about code on demand very briefly because it was an optional 
restraint that we were not interested in exploring for our specific implementation.

Project Specifics:
In the KVS repo is the interworkings of my team and I's Key Value Store (KVS) where we created a library to store data in a HashMap of 
String keys and JSONObject values in which we completed the following tasks:
 - create a new (empty) KVS, or open one that has been written out to storage. 
 - write the KVS in memory out to storage.
 - clear the KVS currently in memory.
 - check if a given key string is present.
 - retrieve the value (a JSON string) associated with a key.
 - modify the value associated with a key (more specifically, we can specify a json field to update for that value).
 - add a new key/value pair.
 - retrieve a list of key/value pairs for which the keys match some search criteria (a regular expression, perhaps).
 
 This then became our backend database for our RESTful API. We were then tasked with creating a RESTful API for a project. A project consisted
 of todolists, tasks, users. Each has their own constructor where we defined what each of these consisted of. Once we came to an agreement on what
 each of these could contain we had to agree on a set of endpoints for each resource. This was accomplished and can be seen in ProjectResource, 
 TodoListResource, TaskResource, and UserResource files. In each of these methods we chose the HTTP verb what it consumed/produced and then dealt
 with the PathParams appropriately. To avoid dumping everything into one class that would have been very difficult to read, we contained the 
 the specifics of each HTTP call in the KVSStorage class. This allows for much easier readability for someone who is not already familiar with the code.
 This was all tested rigorously using Yet Another REST Client (YARC) and worked as intended. 
 
