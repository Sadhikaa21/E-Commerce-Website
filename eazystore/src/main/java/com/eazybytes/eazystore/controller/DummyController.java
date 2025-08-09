package com.eazybytes.eazystore.controller;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.eazybytes.eazystore.dto.UserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import java.util.List;
import java.util.Map;

//WE ARE EXPECTING OUR CREATE USER RESTAPI TO accept the user data and store the same into db
//so we have to make sure that this method is accepting the userdto as input parameter
//inside restapis data is received as json format,to convert json to dto object bts,we need @RequestBody
@RestController
@RequestMapping("api/v1/dummy")
@Validated
public class DummyController {
    @PostMapping("/create-user")
    public String createUser(@RequestBody UserDto UserDto) {
        System.out.println(UserDto);
        return "user created successfully";
    }
    @PostMapping("/request-entity")
    public String createUserWithEntity(RequestEntity<UserDto> requestEntity) {
        HttpHeaders header = requestEntity.getHeaders();
        UserDto userDto = requestEntity.getBody();
        return "User created successfully";
//    headers in httpheaders format so we receive it in an obj of that type,header is just variable to catch the contents,same with next line
//    data for getting the body here is sent as input in postman
    }
    @GetMapping("/headers")
    public String readHeaders(@RequestHeader HttpHeaders headers) {
        List<String> location= headers.get("User-Location");
        return "Recevied headers with value : " + headers.toString();
    }
    @GetMapping("/search")
    public String searchUser(@Size(min=5,max=30) @RequestParam(required = false, defaultValue = "Guest",
            name = "name") String userName) {
        return "Searching for user : " + userName;
    }
    @GetMapping("/multiple-search")
    public String multipleSearch(@RequestParam Map<String,String> params) {
        return "Searching for user : " + params.get("firstName") + " " + params.get("lastName");
    }
    @GetMapping({"/user/{userId}/posts/{postId}", "/user/{userId}"})
    public String getUser(@PathVariable(name = "userId") String id,
                          @PathVariable(required = false) String postId) {
        return "Searching for user : " + id + " and post : " + postId;
    }

    @GetMapping({"/user/map/{userId}/posts/{postId}", "/user/map/{userId}"})
    public String getUserUsingMap(@PathVariable Map<String,String> pathVariables) {
        return "Searching for user : " + pathVariables.get("userId") + " and post : "
                + pathVariables.get("postId");
    }
}

//so what happens is "http://localhost:8080/api/v1/dummy/create-user" is the postman request sent with POST restapi,and we also send sample data in postman itself--body,raw,json...{
//    "name" : "Madan Reddy",
//    "mobileNumber" : "1221212121",
//    "email" : "tutor@eazybytes.com"
//}
//so now request is accepted and data is populated with strings defined in userdto and json conv to dto using reqbody..then whatever in createuser block is executed.
//for get requests we don't get data from db,from what we ask in query line itself.here in postman