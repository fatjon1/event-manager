# event-manager using jjwt

<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>

Todo: if passed a not valid jwt adding an extra char to the jwt the app goes in infinit loop
    // io.jsonwebtoken.MalformedJwtException: Unable to read JSON value: �쉅����!L��؉
    // in postman get the following error
    // Error: Exceeded maxRedirects. Probably stuck in a redirect loop http://localhost:8080/login
