
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity(name = "users")
class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
            val id: Int = 0

            @Column(name = "user_name")
            var userName: String = ""

            @Column(name = "password")
            var password: String = ""


    constructor() {}
    constructor(name: String?, password : String?): super(){
        this.userName = name?:""
        this.password = BCryptPasswordEncoder().encode(password)?:""
    }

    fun isPasswordValid(password: String?):Boolean{
        return BCryptPasswordEncoder()
            .matches(password,this.password)
    }

    override fun toString(): String {
        return "User(id=$id, userName='$userName', password='$password')"
    }


}
