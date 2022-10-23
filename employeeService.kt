package com.nevin.kotlindemo.service


import com.example.kotlindemo.entities.User
import com.example.kotlindemo.model.Employee
import com.example.kotlindemo.repository.EmployeeRepository
import com.example.kotlindemo.repository.UserRepository
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

@Service
class EmployeeService {

    val backgroundDispatcher = newFixedThreadPoolContext(5, "App Background")

    val dbDispatcher = backgroundDispatcher.limitedParallelism(3)

    @Autowired
    private lateinit var  employeeRepository: EmployeeRepository

    @Autowired
    private lateinit var  userRepository: UserRepository

    fun addUser(employee: Employee) : Employee{
    return employeeRepository.save(employee);
    }
    fun getEmpByName(value : String) : List<Employee> {

        return employeeRepository.findByFirstName(value);
    }


    fun tryCoroutines() : List<Any> = runBlocking{

//        CoroutineScope(Dispatchers.IO).launch{
//            val empList   = async {
//                getEmployees()
//            }
//            val userList = async {
//                getUsers()
//            }
//            println(LocalDateTime.now())
//            val employeeList = empList.await()
//            val usersList = userList.await()
//            println(employeeList)
//            println(usersList)
//            println(LocalDateTime.now())
//        }
      //  runBlocking {
        println(LocalDateTime.now())
            val empResponse   = async {
                getEmployees()
            }
            val userResponse = async {
                getUsers()
            }
            val collect = mutableListOf<Any>(userResponse.await(),empResponse.await())
        println(LocalDateTime.now())
            collect
     //   }


    }


    suspend fun getEmployees() : List<Employee> = withContext(dbDispatcher){
        delay(2000)
        println(Thread.currentThread().name)
        return@withContext employeeRepository.findAll()


    }

    suspend fun getUsers(): List<User> = withContext(dbDispatcher){
        delay(2000)
        println(Thread.currentThread().name)
        return@withContext userRepository.findAll()
    }
}
