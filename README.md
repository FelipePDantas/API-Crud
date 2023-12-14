<h1>Crud Básico</h1>

> Neste projeto prático tento aprofundar nós reais conceitos de um CRUD , em quesito dos métodos de requisição HTTP mais usados no dia a dia,
> abordando o GET,POST,PUT,PATCH e DELETE bem simples e prático em uma API em <https://spring.io/guides/gs/spring-boot/> com Java .

<h3>Camada de Service do padrão MVC</h3>
<p>A camadda de Service é utilizada para conter a regra de negocio, serve também como uma interface entre o Controller e o Model 
  deixando assim a regra de negocio mais limpa e modular , deixando cada método bem mais legivel e facil de se entender deixando bem nitido o Single Responsiblity Principle  </p>


  ~~~java
    @Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        var userSave = repository.save(user);
        return userSave;
    }
~~~
*A injeção de dependência com a anotação @Autowired é um recurso do Spring onde facilita muito reduzindo o acoplamento entre as classes.
*Já no metódo save , que passamos um User como retorno que é uma class na minha camada <i>Model</i>
*
