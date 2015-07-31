package adeady.benchmarks.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import groovy.transform.ToString

@ToString
@JsonIgnoreProperties(["user"])
class UserRepresentation {

    String name
    String id
}
