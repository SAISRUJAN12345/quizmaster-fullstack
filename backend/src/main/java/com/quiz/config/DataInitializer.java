package com.quiz.config;

import com.quiz.model.Question;
import com.quiz.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final QuestionRepository questionRepository;

    public DataInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        if (questionRepository.count() == 0) {
            List<Question> sampleQuestions = List.of(
                    // --- Java & Spring ---
                    new Question(
                            "Which Java collection class maintains insertion order and allows null elements?",
                            Arrays.asList("HashSet", "TreeSet", "LinkedHashSet", "PriorityQueue"),
                            2,
                            "LinkedHashSet maintains a doubly-linked list running through all of its entries, preserving insertion order while allowing null elements.",
                            "Java & Spring",
                            "Medium"
                    ),
                    new Question(
                            "What is the primary function of the Java Virtual Machine (JVM)?",
                            Arrays.asList(
                                    "Compiles Java source code directly into native assembly",
                                    "Executes compiled Java bytecode across different host platforms",
                                    "Provides a version control system for Java packages",
                                    "Minifies and obfuscates JavaScript scripts"
                            ),
                            1,
                            "The JVM is an execution engine that translates compiled Java bytecode into platform-specific machine code, enabling 'Write Once, Run Anywhere'.",
                            "Java & Spring",
                            "Easy"
                    ),
                    new Question(
                            "In Spring Boot, which annotation marks a class as a REST controller combining @Controller and @ResponseBody?",
                            Arrays.asList("@RestController", "@Service", "@Repository", "@Configuration"),
                            0,
                            "@RestController is a convenience annotation combining @Controller and @ResponseBody, ensuring method return values serialize directly into JSON/XML.",
                            "Java & Spring",
                            "Easy"
                    ),
                    new Question(
                            "Which annotation in Spring Data JPA is used to define the primary key of an entity?",
                            Arrays.asList("@Column", "@Id", "@Key", "@PrimaryKey"),
                            1,
                            "@Id marks a field in an entity as the primary key of the corresponding database table.",
                            "Java & Spring",
                            "Easy"
                    ),

                    // --- Frontend & Web ---
                    new Question(
                            "In React, which hook is primarily used for declaring and updating local component state?",
                            Arrays.asList("useEffect", "useState", "useContext", "useMemo"),
                            1,
                            "useState is the fundamental React hook that adds local state variables to functional components.",
                            "Frontend & Web",
                            "Easy"
                    ),
                    new Question(
                            "Which React hook is designed to manage side-effects like data fetching and DOM mutations?",
                            Arrays.asList("useCallback", "useReducer", "useEffect", "useRef"),
                            2,
                            "useEffect allows functional components to execute side-effects after rendering, such as fetching data or setting up subscriptions.",
                            "Frontend & Web",
                            "Easy"
                    ),
                    new Question(
                            "Which CSS display property creates a one-dimensional flexible layout container?",
                            Arrays.asList("display: block", "display: flex", "display: grid", "display: table"),
                            1,
                            "'display: flex' enables the Flexible Box layout model for linear 1D row or column space distribution.",
                            "Frontend & Web",
                            "Easy"
                    ),
                    new Question(
                            "What does the Virtual DOM in React primarily achieve?",
                            Arrays.asList(
                                    "Directly writes raw pixels to GPU memory",
                                    "Minimizes expensive real DOM manipulations via reconciliation diffing",
                                    "Stores database records inside the client browser",
                                    "Encrypts all HTTP traffic automatically"
                            ),
                            1,
                            "The Virtual DOM is an in-memory representation that React uses to compute minimal diffs before applying batched updates to the real DOM.",
                            "Frontend & Web",
                            "Medium"
                    ),

                    // --- Databases & Architecture ---
                    new Question(
                            "Which HTTP status code represents that a request succeeded and a new resource has been created?",
                            Arrays.asList("200 OK", "201 Created", "204 No Content", "302 Found"),
                            1,
                            "HTTP 201 Created indicates that the request succeeded and led to the creation of a new resource on the server.",
                            "Databases & Architecture",
                            "Easy"
                    ),
                    new Question(
                            "In relational databases, which ACID property guarantees transactions are fully completed or rolled back completely?",
                            Arrays.asList("Atomicity", "Consistency", "Isolation", "Durability"),
                            0,
                            "Atomicity guarantees an 'all-or-nothing' execution: if any step of a transaction fails, the entire transaction rolls back.",
                            "Databases & Architecture",
                            "Medium"
                    ),
                    new Question(
                            "What is the main advantage of an in-memory database like H2 during integration testing?",
                            Arrays.asList(
                                    "Provides infinite disk storage without configuration",
                                    "Fast in-memory execution and isolated test cycles without external server dependencies",
                                    "Automatically bypasses all SQL syntax validation",
                                    "Stores data forever even after power loss"
                            ),
                            1,
                            "H2 in-memory databases start instantly, require zero host server setup, and cleanly reset between test runs.",
                            "Databases & Architecture",
                            "Easy"
                    ),
                    new Question(
                            "What is the HTTP method idempotency concept in RESTful API design?",
                            Arrays.asList(
                                    "A method that can only be invoked once per user session",
                                    "A method where making multiple identical requests has the same effect as making a single request",
                                    "A method that always encrypts the response body",
                                    "A method that requires token-based authentication"
                            ),
                            1,
                            "An idempotent HTTP method (like GET, PUT, DELETE) produces the same resource state whether called once or multiple times consecutively.",
                            "Databases & Architecture",
                            "Hard"
                    )
            );

            questionRepository.saveAll(sampleQuestions);
            System.out.println(">>> Sample quiz questions initialized in database. Total: " + sampleQuestions.size());
        }
    }
}
