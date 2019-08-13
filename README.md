# Assignment Scalable Web

Provide 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints <host>/v1/diff/<ID>/left /v1/diff//right The provided data needs to be diff-ed and the results shall be available on a third end point /v1/diff/ The results shall provide the following info in JSON format

 - If equal return that
 - If not of equal size just return that
 - If of same size provide insight in where the diffs are, actual diffs are not needed. So mainly offsets + length in the data Make assumptions in the implementation explicit, choices are good but need to be communicated    

# Built With

  - Spring Boot 2.1.7
  - JUnit Jupiter 5.4
  - Mockito 2.24
  - Lombok
  - Swagger 2

# Usage
Tomcat and H2 are embeded so you basically run the tests and the application will be available on http://localhost:8080/

Swagger Url : /v2/api-docs

# Author

Igor Furtado 


# Improvements

- Merge Both Upload(Left and Right) Rest to One with "Side" inside of the JSON.
- Create Rest service to return the Entity of Created File(Should be used on the return of Merged Service(Left and Right) as HATEAOS pattern).
- Create Rest(PUT) to update the Base64.
- Find the best way to store files according to customer needs(Base64, Blob, FileSystem).



