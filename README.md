<h1>Crud Básico</h1>

> Neste projeto prático tento aprofundar nós reais conceitos de um CRUD , em quesito dos métodos de requisição HTTP mais usados no dia a dia,
> abordando o GET,POST,PUT,PATCH e DELETE bem simples e prático em uma API em <https://spring.io/guides/gs/spring-boot/> com Java .

<h3>Camada de Service do padrão MVC</h3>
<p>A camadda de Service é utilizada para conter a regra de negocio, serve também como uma interface entre o Controller e o Model 
  deixando assim a regra de negocio mais limpa e modular , deixando cada método bem mais legivel e facil de se entender deixando bem nitido o <code>Single Responsiblity Principle</code> </p>


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
* A injeção de dependência com a anotação <code>@Autowired</code> é um recurso do Spring onde facilita muito reduzindo o acoplamento entre as classes.
* Já no metódo save , que passamos um **User** como retorno que é uma class na minha camada <code>Model</code> que usamos o metódo da camada <code>UserRepository</code> onde essa camada é responsavel pela persistência de dados , ela possui seus próprios metódos para realizar leitura,criar,atualização e deleção **(CRUD)**.
* No exemplo acima podemos ver um dos métodos **repository.save();** esse método é reponsável por criar a entidade e persistir os dados .

<h4>Métodos de Leitura</h4>

~~~java 
 public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findByID(Long id) {
        return repository.findById(id);
    }
~~~
* Nesses dois métodos utilizamos para fazer a letura(busca) por entidades no meu Banco de Dados.
* No primeiro método buscamos todos os User em uma estrutura List que irá trazer toda entidade no nosso banco pois o <code>repository.findAll</code> retornará todas as entidades que foram persistidas.
* Já no segundo método a busca é pelo **ID** do **User** uqe é passado como parâmetro , por isso usamos um <code>Optional</code> porque há a possibilidade de informar um **ID** que não existe, se o **ID** existir será exibido o **User** daquele respectivo **ID**..
