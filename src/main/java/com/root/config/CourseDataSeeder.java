package com.root.config;

import com.root.beans.Course;
import com.root.beans.Lesson;
import com.root.repositories.CourseRepository;
import com.root.repositories.LessonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CourseDataSeeder {

    @Bean
    CommandLineRunner seedCourses(CourseRepository courseRepository, LessonRepository lessonRepository) {
        return args -> {
            if (courseRepository.count() > 0) {
                return;
            }

            // ═══════════════════════════════════════════════════════════════
            // COURSE 1: Java Programming
            // ═══════════════════════════════════════════════════════════════
            Course java = new Course();
            java.setTitle("Java Programming");
            java.setDescription("Master Java from the ground up. Learn syntax, variables, data types, operators, control flow and loops with hands-on examples.");
            java.setCategory("Programming");
            java.setDifficultyLevel("Beginner");
            java.setEstimatedHours(12);
            java.setTotalLessons(6);
            courseRepository.save(java);

            saveLesson(lessonRepository, java, 1, "Introduction to Java",
                "<h2>What is Java?</h2>" +
                "<p>Java is a high-level, object-oriented programming language developed by Sun Microsystems in 1995. It is designed to be platform-independent, meaning code written in Java can run on any device that has the Java Virtual Machine (JVM) installed. This principle is often summarized as <strong>\"Write Once, Run Anywhere\"</strong>.</p>" +
                "<h3>Key Features of Java</h3>" +
                "<ul>" +
                "<li><strong>Platform Independent</strong> — Java source code is compiled into bytecode, which runs on the JVM regardless of the underlying operating system.</li>" +
                "<li><strong>Object-Oriented</strong> — Everything in Java revolves around classes and objects, making code modular and reusable.</li>" +
                "<li><strong>Strongly Typed</strong> — Every variable must be declared with a data type, reducing runtime errors.</li>" +
                "<li><strong>Automatic Memory Management</strong> — Java uses garbage collection to automatically free unused memory.</li>" +
                "<li><strong>Rich Standard Library</strong> — The Java API provides thousands of ready-to-use classes for networking, I/O, data structures, and more.</li>" +
                "</ul>" +
                "<h3>Java Architecture</h3>" +
                "<p>Java programs go through three stages:</p>" +
                "<ol>" +
                "<li><strong>Write</strong> — You write source code in <code>.java</code> files.</li>" +
                "<li><strong>Compile</strong> — The Java compiler (<code>javac</code>) converts source code into bytecode (<code>.class</code> files).</li>" +
                "<li><strong>Run</strong> — The JVM interprets or JIT-compiles the bytecode and executes it on your machine.</li>" +
                "</ol>" +
                "<h3>Why Learn Java?</h3>" +
                "<p>Java is one of the most widely used languages in the world. It powers Android apps, enterprise backend systems, web applications, and large-scale distributed systems. Companies like Google, Amazon, and Netflix rely heavily on Java. Learning Java opens doors to a vast ecosystem of tools, frameworks like Spring, and a strong job market.</p>");

            saveLesson(lessonRepository, java, 2, "Java Syntax and Hello World",
                "<h2>Your First Java Program</h2>" +
                "<p>Every Java program must have at least one class and a <code>main</code> method. The <code>main</code> method is the entry point where program execution begins.</p>" +
                "<pre><code>public class HelloWorld {\n    public static void main(String[] args) {\n        System.out.println(\"Hello, World!\");\n    }\n}</code></pre>" +
                "<h3>Breaking Down the Code</h3>" +
                "<ul>" +
                "<li><code>public class HelloWorld</code> — Declares a public class named <code>HelloWorld</code>. The filename must match the class name.</li>" +
                "<li><code>public static void main(String[] args)</code> — The main method. <code>public</code> makes it accessible from anywhere, <code>static</code> means it belongs to the class itself, <code>void</code> means it returns nothing, and <code>String[] args</code> accepts command-line arguments.</li>" +
                "<li><code>System.out.println()</code> — Prints text to the console followed by a new line.</li>" +
                "</ul>" +
                "<h3>Comments in Java</h3>" +
                "<pre><code>// This is a single-line comment\n\n/* This is a\n   multi-line comment */\n\n/** This is a Javadoc comment\n  * used for documentation */</code></pre>" +
                "<h3>Important Rules</h3>" +
                "<ul>" +
                "<li>Every statement ends with a semicolon <code>;</code></li>" +
                "<li>Java is case-sensitive — <code>Main</code> and <code>main</code> are different.</li>" +
                "<li>Code blocks are enclosed in curly braces <code>{ }</code>.</li>" +
                "<li>The file name must exactly match the public class name.</li>" +
                "</ul>");

            saveLesson(lessonRepository, java, 3, "Variables and Data Types",
                "<h2>Variables in Java</h2>" +
                "<p>A variable is a container that holds data. In Java, every variable must be declared with a specific data type before it can be used.</p>" +
                "<pre><code>int age = 25;\ndouble salary = 55000.50;\nchar grade = 'A';\nboolean isActive = true;\nString name = \"John\";</code></pre>" +
                "<h3>Primitive Data Types</h3>" +
                "<table>" +
                "<tr><th>Type</th><th>Size</th><th>Description</th><th>Example</th></tr>" +
                "<tr><td><code>byte</code></td><td>1 byte</td><td>Whole numbers -128 to 127</td><td><code>byte b = 100;</code></td></tr>" +
                "<tr><td><code>short</code></td><td>2 bytes</td><td>Whole numbers -32,768 to 32,767</td><td><code>short s = 5000;</code></td></tr>" +
                "<tr><td><code>int</code></td><td>4 bytes</td><td>Whole numbers up to ~2 billion</td><td><code>int i = 100000;</code></td></tr>" +
                "<tr><td><code>long</code></td><td>8 bytes</td><td>Very large whole numbers</td><td><code>long l = 15000000000L;</code></td></tr>" +
                "<tr><td><code>float</code></td><td>4 bytes</td><td>Decimal numbers (6-7 digits)</td><td><code>float f = 5.75f;</code></td></tr>" +
                "<tr><td><code>double</code></td><td>8 bytes</td><td>Decimal numbers (15 digits)</td><td><code>double d = 19.99;</code></td></tr>" +
                "<tr><td><code>boolean</code></td><td>1 bit</td><td>true or false</td><td><code>boolean b = true;</code></td></tr>" +
                "<tr><td><code>char</code></td><td>2 bytes</td><td>A single character</td><td><code>char c = 'A';</code></td></tr>" +
                "</table>" +
                "<h3>Type Casting</h3>" +
                "<pre><code>// Widening (automatic) — smaller to larger type\nint myInt = 9;\ndouble myDouble = myInt; // 9.0\n\n// Narrowing (manual) — larger to smaller type\ndouble x = 9.78;\nint y = (int) x; // 9</code></pre>" +
                "<h3>Constants</h3>" +
                "<p>Use the <code>final</code> keyword to declare a constant whose value cannot change:</p>" +
                "<pre><code>final double PI = 3.14159;</code></pre>");

            saveLesson(lessonRepository, java, 4, "Operators in Java",
                "<h2>Java Operators</h2>" +
                "<p>Operators are special symbols used to perform operations on variables and values. Java divides operators into several groups.</p>" +
                "<h3>Arithmetic Operators</h3>" +
                "<pre><code>int a = 10, b = 3;\nSystem.out.println(a + b);  // 13 (Addition)\nSystem.out.println(a - b);  // 7  (Subtraction)\nSystem.out.println(a * b);  // 30 (Multiplication)\nSystem.out.println(a / b);  // 3  (Division — integer)\nSystem.out.println(a % b);  // 1  (Modulus — remainder)</code></pre>" +
                "<h3>Assignment Operators</h3>" +
                "<pre><code>int x = 10;\nx += 5;  // x = x + 5 → 15\nx -= 3;  // x = x - 3 → 12\nx *= 2;  // x = x * 2 → 24\nx /= 4;  // x = x / 4 → 6\nx %= 4;  // x = x % 4 → 2</code></pre>" +
                "<h3>Comparison Operators</h3>" +
                "<pre><code>int a = 5, b = 8;\nSystem.out.println(a == b); // false\nSystem.out.println(a != b); // true\nSystem.out.println(a > b);  // false\nSystem.out.println(a < b);  // true\nSystem.out.println(a >= 5); // true\nSystem.out.println(b <= 7); // false</code></pre>" +
                "<h3>Logical Operators</h3>" +
                "<pre><code>boolean x = true, y = false;\nSystem.out.println(x && y); // false (AND)\nSystem.out.println(x || y); // true  (OR)\nSystem.out.println(!x);     // false (NOT)</code></pre>" +
                "<h3>Increment and Decrement</h3>" +
                "<pre><code>int count = 5;\ncount++;  // 6 (post-increment)\ncount--;  // 5 (post-decrement)\n++count;  // 6 (pre-increment)</code></pre>" +
                "<h3>Ternary Operator</h3>" +
                "<pre><code>int age = 20;\nString status = (age >= 18) ? \"Adult\" : \"Minor\";\n// status = \"Adult\"</code></pre>");

            saveLesson(lessonRepository, java, 5, "Control Flow Statements",
                "<h2>Decision Making in Java</h2>" +
                "<p>Control flow statements allow your program to make decisions and execute different code blocks based on conditions.</p>" +
                "<h3>if Statement</h3>" +
                "<pre><code>int temperature = 30;\nif (temperature > 25) {\n    System.out.println(\"It's a hot day.\");\n}</code></pre>" +
                "<h3>if-else Statement</h3>" +
                "<pre><code>int age = 16;\nif (age >= 18) {\n    System.out.println(\"You can vote.\");\n} else {\n    System.out.println(\"You cannot vote yet.\");\n}</code></pre>" +
                "<h3>else-if Ladder</h3>" +
                "<pre><code>int score = 75;\nif (score >= 90) {\n    System.out.println(\"Grade: A\");\n} else if (score >= 80) {\n    System.out.println(\"Grade: B\");\n} else if (score >= 70) {\n    System.out.println(\"Grade: C\");\n} else if (score >= 60) {\n    System.out.println(\"Grade: D\");\n} else {\n    System.out.println(\"Grade: F\");\n}\n// Output: Grade: C</code></pre>" +
                "<h3>switch Statement</h3>" +
                "<pre><code>int day = 3;\nswitch (day) {\n    case 1:\n        System.out.println(\"Monday\");\n        break;\n    case 2:\n        System.out.println(\"Tuesday\");\n        break;\n    case 3:\n        System.out.println(\"Wednesday\");\n        break;\n    default:\n        System.out.println(\"Other day\");\n}\n// Output: Wednesday</code></pre>" +
                "<h3>Nested if</h3>" +
                "<pre><code>int age = 25;\nboolean hasLicense = true;\nif (age >= 18) {\n    if (hasLicense) {\n        System.out.println(\"You can drive.\");\n    } else {\n        System.out.println(\"Get a license first.\");\n    }\n}</code></pre>");

            saveLesson(lessonRepository, java, 6, "Loops in Java",
                "<h2>Repetition with Loops</h2>" +
                "<p>Loops allow you to execute a block of code repeatedly. Java provides three main loop constructs.</p>" +
                "<h3>for Loop</h3>" +
                "<p>Use when you know how many times to iterate:</p>" +
                "<pre><code>for (int i = 1; i <= 5; i++) {\n    System.out.println(\"Count: \" + i);\n}\n// Output: Count: 1, Count: 2, ... Count: 5</code></pre>" +
                "<h3>while Loop</h3>" +
                "<p>Use when the number of iterations is unknown — repeats while condition is true:</p>" +
                "<pre><code>int i = 1;\nwhile (i <= 5) {\n    System.out.println(\"Count: \" + i);\n    i++;\n}</code></pre>" +
                "<h3>do-while Loop</h3>" +
                "<p>Executes at least once, then checks condition:</p>" +
                "<pre><code>int i = 1;\ndo {\n    System.out.println(\"Count: \" + i);\n    i++;\n} while (i <= 5);</code></pre>" +
                "<h3>break and continue</h3>" +
                "<pre><code>// break — exit loop immediately\nfor (int i = 0; i < 10; i++) {\n    if (i == 5) break;\n    System.out.println(i); // prints 0-4\n}\n\n// continue — skip current iteration\nfor (int i = 0; i < 10; i++) {\n    if (i % 2 == 0) continue;\n    System.out.println(i); // prints 1,3,5,7,9\n}</code></pre>" +
                "<h3>Enhanced for Loop (for-each)</h3>" +
                "<pre><code>String[] fruits = {\"Apple\", \"Banana\", \"Cherry\"};\nfor (String fruit : fruits) {\n    System.out.println(fruit);\n}</code></pre>" +
                "<h3>Nested Loops</h3>" +
                "<pre><code>for (int i = 1; i <= 3; i++) {\n    for (int j = 1; j <= 3; j++) {\n        System.out.print(i * j + \" \");\n    }\n    System.out.println();\n}\n// Output:\n// 1 2 3\n// 2 4 6\n// 3 6 9</code></pre>");

            // ═══════════════════════════════════════════════════════════════
            // COURSE 2: Python Programming
            // ═══════════════════════════════════════════════════════════════
            Course python = new Course();
            python.setTitle("Python Programming");
            python.setDescription("Learn Python from scratch. Cover syntax, variables, data types, operators, control flow and functions with practical examples.");
            python.setCategory("Programming");
            python.setDifficultyLevel("Beginner");
            python.setEstimatedHours(10);
            python.setTotalLessons(6);
            courseRepository.save(python);

            saveLesson(lessonRepository, python, 1, "Introduction to Python",
                "<h2>What is Python?</h2>" +
                "<p>Python is a high-level, interpreted programming language created by Guido van Rossum and first released in 1991. It emphasizes code readability with its clean syntax and uses indentation to define code blocks instead of curly braces.</p>" +
                "<h3>Why Python?</h3>" +
                "<ul>" +
                "<li><strong>Easy to Learn</strong> — Python's syntax is clear and intuitive, making it ideal for beginners.</li>" +
                "<li><strong>Versatile</strong> — Used in web development, data science, machine learning, automation, scripting, and more.</li>" +
                "<li><strong>Large Community</strong> — A massive ecosystem of libraries (NumPy, Pandas, Django, Flask, TensorFlow) and community support.</li>" +
                "<li><strong>Interpreted</strong> — No compilation step; code runs line by line, making debugging easier.</li>" +
                "<li><strong>Cross-platform</strong> — Runs on Windows, macOS, and Linux without modification.</li>" +
                "</ul>" +
                "<h3>Python Applications</h3>" +
                "<p>Python is used across many domains:</p>" +
                "<ul>" +
                "<li><strong>Web Development</strong> — Django, Flask frameworks</li>" +
                "<li><strong>Data Science</strong> — Pandas, NumPy, Matplotlib</li>" +
                "<li><strong>Machine Learning</strong> — TensorFlow, scikit-learn, PyTorch</li>" +
                "<li><strong>Automation</strong> — Scripts to automate repetitive tasks</li>" +
                "<li><strong>Desktop Applications</strong> — Tkinter, PyQt</li>" +
                "</ul>" +
                "<h3>Python 2 vs Python 3</h3>" +
                "<p>Python 2 reached end-of-life in January 2020. All new projects should use <strong>Python 3</strong>, which is the current and actively maintained version. This course uses Python 3 syntax throughout.</p>");

            saveLesson(lessonRepository, python, 2, "Python Syntax and First Program",
                "<h2>Your First Python Program</h2>" +
                "<p>Python programs are simple to write. Unlike Java or C++, you do not need a class or main method. You can write and run code directly.</p>" +
                "<pre><code>print(\"Hello, World!\")</code></pre>" +
                "<p>That single line is a complete Python program! The <code>print()</code> function outputs text to the console.</p>" +
                "<h3>Indentation Matters</h3>" +
                "<p>Python uses <strong>indentation</strong> (whitespace at the beginning of a line) to define code blocks. This replaces the curly braces used in other languages.</p>" +
                "<pre><code>if 5 > 2:\n    print(\"Five is greater than two!\")\n    print(\"This is still inside the if block\")\nprint(\"This is outside the if block\")</code></pre>" +
                "<p><strong>Important:</strong> Inconsistent indentation will cause an <code>IndentationError</code>.</p>" +
                "<h3>Comments</h3>" +
                "<pre><code># This is a single-line comment\nprint(\"Hello\")  # This is an inline comment\n\n\"\"\"\nThis is a multi-line\ncomment (docstring)\n\"\"\"</code></pre>" +
                "<h3>Python is Case-Sensitive</h3>" +
                "<pre><code>name = \"Alice\"\nName = \"Bob\"\n# name and Name are two different variables</code></pre>" +
                "<h3>Multiple Statements</h3>" +
                "<pre><code># Multiple assignments\nx, y, z = 1, 2, 3\n\n# Same value to multiple variables\na = b = c = 0\n\n# Print multiple values\nprint(x, y, z)  # Output: 1 2 3</code></pre>");

            saveLesson(lessonRepository, python, 3, "Variables and Data Types",
                "<h2>Variables in Python</h2>" +
                "<p>In Python, you do not need to declare a variable type. Python automatically determines the type based on the value you assign.</p>" +
                "<pre><code>name = \"Alice\"      # str (string)\nage = 25             # int (integer)\nheight = 5.7         # float\nis_student = True    # bool (boolean)</code></pre>" +
                "<h3>Data Types</h3>" +
                "<table>" +
                "<tr><th>Type</th><th>Description</th><th>Example</th></tr>" +
                "<tr><td><code>str</code></td><td>Text/string</td><td><code>\"Hello\"</code></td></tr>" +
                "<tr><td><code>int</code></td><td>Whole number</td><td><code>42</code></td></tr>" +
                "<tr><td><code>float</code></td><td>Decimal number</td><td><code>3.14</code></td></tr>" +
                "<tr><td><code>bool</code></td><td>True or False</td><td><code>True</code></td></tr>" +
                "<tr><td><code>list</code></td><td>Ordered, mutable collection</td><td><code>[1, 2, 3]</code></td></tr>" +
                "<tr><td><code>tuple</code></td><td>Ordered, immutable collection</td><td><code>(1, 2, 3)</code></td></tr>" +
                "<tr><td><code>dict</code></td><td>Key-value pairs</td><td><code>{\"a\": 1}</code></td></tr>" +
                "<tr><td><code>set</code></td><td>Unordered, unique items</td><td><code>{1, 2, 3}</code></td></tr>" +
                "</table>" +
                "<h3>Checking Data Type</h3>" +
                "<pre><code>x = 10\nprint(type(x))  # &lt;class 'int'&gt;\n\ny = \"Hello\"\nprint(type(y))  # &lt;class 'str'&gt;</code></pre>" +
                "<h3>Type Conversion</h3>" +
                "<pre><code>x = \"10\"\ny = int(x)      # Convert string to int\nz = float(x)    # Convert string to float\nw = str(42)     # Convert int to string</code></pre>" +
                "<h3>String Operations</h3>" +
                "<pre><code>greeting = \"Hello\"\nname = \"World\"\nresult = greeting + \" \" + name  # Concatenation\nprint(result)  # Hello World\nprint(len(result))  # 11\nprint(result.upper())  # HELLO WORLD</code></pre>");

            saveLesson(lessonRepository, python, 4, "Operators in Python",
                "<h2>Python Operators</h2>" +
                "<p>Operators perform operations on variables and values.</p>" +
                "<h3>Arithmetic Operators</h3>" +
                "<pre><code>a, b = 10, 3\nprint(a + b)   # 13 (Addition)\nprint(a - b)   # 7  (Subtraction)\nprint(a * b)   # 30 (Multiplication)\nprint(a / b)   # 3.333... (Division — always float)\nprint(a // b)  # 3  (Floor division — integer)\nprint(a % b)   # 1  (Modulus — remainder)\nprint(a ** b)  # 1000 (Exponentiation — 10^3)</code></pre>" +
                "<h3>Comparison Operators</h3>" +
                "<pre><code>x, y = 5, 8\nprint(x == y)  # False\nprint(x != y)  # True\nprint(x > y)   # False\nprint(x < y)   # True\nprint(x >= 5)  # True\nprint(y <= 7)  # False</code></pre>" +
                "<h3>Logical Operators</h3>" +
                "<pre><code>x = True\ny = False\nprint(x and y)  # False\nprint(x or y)   # True\nprint(not x)    # False</code></pre>" +
                "<h3>Assignment Operators</h3>" +
                "<pre><code>x = 10\nx += 5   # x = 15\nx -= 3   # x = 12\nx *= 2   # x = 24\nx /= 4   # x = 6.0\nx //= 2  # x = 3.0\nx **= 3  # x = 27.0</code></pre>" +
                "<h3>Membership Operators</h3>" +
                "<pre><code>fruits = [\"apple\", \"banana\", \"cherry\"]\nprint(\"banana\" in fruits)      # True\nprint(\"grape\" not in fruits)   # True</code></pre>" +
                "<h3>Identity Operators</h3>" +
                "<pre><code>a = [1, 2, 3]\nb = a\nc = [1, 2, 3]\nprint(a is b)      # True (same object)\nprint(a is c)      # False (different object, same value)\nprint(a is not c)  # True</code></pre>");

            saveLesson(lessonRepository, python, 5, "Control Flow in Python",
                "<h2>Decision Making in Python</h2>" +
                "<h3>if Statement</h3>" +
                "<pre><code>temperature = 30\nif temperature > 25:\n    print(\"It's a hot day\")</code></pre>" +
                "<h3>if-else Statement</h3>" +
                "<pre><code>age = 16\nif age >= 18:\n    print(\"You can vote\")\nelse:\n    print(\"You cannot vote yet\")</code></pre>" +
                "<h3>if-elif-else (else if)</h3>" +
                "<pre><code>score = 75\nif score >= 90:\n    grade = \"A\"\nelif score >= 80:\n    grade = \"B\"\nelif score >= 70:\n    grade = \"C\"\nelif score >= 60:\n    grade = \"D\"\nelse:\n    grade = \"F\"\nprint(f\"Your grade: {grade}\")  # Your grade: C</code></pre>" +
                "<h3>Ternary (Conditional) Expression</h3>" +
                "<pre><code>age = 20\nstatus = \"Adult\" if age >= 18 else \"Minor\"\nprint(status)  # Adult</code></pre>" +
                "<h3>Nested if</h3>" +
                "<pre><code>num = 15\nif num > 0:\n    if num % 2 == 0:\n        print(\"Positive even number\")\n    else:\n        print(\"Positive odd number\")\nelse:\n    print(\"Non-positive number\")\n# Output: Positive odd number</code></pre>" +
                "<h3>Logical Conditions in if</h3>" +
                "<pre><code>age = 25\nhas_license = True\n\nif age >= 18 and has_license:\n    print(\"You can drive\")\n\nday = \"Saturday\"\nif day == \"Saturday\" or day == \"Sunday\":\n    print(\"It's the weekend!\")</code></pre>");

            saveLesson(lessonRepository, python, 6, "Functions in Python",
                "<h2>Defining and Using Functions</h2>" +
                "<p>A function is a reusable block of code that performs a specific task. Functions help organize code and avoid repetition.</p>" +
                "<h3>Defining a Function</h3>" +
                "<pre><code>def greet():\n    print(\"Hello, Welcome!\")\n\ngreet()  # Call the function</code></pre>" +
                "<h3>Function Parameters</h3>" +
                "<pre><code>def greet(name):\n    print(f\"Hello, {name}!\")\n\ngreet(\"Alice\")  # Hello, Alice!\ngreet(\"Bob\")    # Hello, Bob!</code></pre>" +
                "<h3>Default Parameters</h3>" +
                "<pre><code>def greet(name, greeting=\"Hello\"):\n    print(f\"{greeting}, {name}!\")\n\ngreet(\"Alice\")              # Hello, Alice!\ngreet(\"Bob\", \"Good morning\") # Good morning, Bob!</code></pre>" +
                "<h3>Return Values</h3>" +
                "<pre><code>def add(a, b):\n    return a + b\n\nresult = add(5, 3)\nprint(result)  # 8\n\n# Return multiple values\ndef get_min_max(numbers):\n    return min(numbers), max(numbers)\n\nlo, hi = get_min_max([3, 1, 7, 2, 9])\nprint(lo, hi)  # 1 9</code></pre>" +
                "<h3>*args and **kwargs</h3>" +
                "<pre><code># *args — variable number of positional arguments\ndef total(*numbers):\n    return sum(numbers)\n\nprint(total(1, 2, 3, 4))  # 10\n\n# **kwargs — variable number of keyword arguments\ndef info(**data):\n    for key, value in data.items():\n        print(f\"{key}: {value}\")\n\ninfo(name=\"Alice\", age=25, city=\"NYC\")</code></pre>" +
                "<h3>Lambda Functions</h3>" +
                "<pre><code># Short anonymous functions\nsquare = lambda x: x ** 2\nprint(square(5))  # 25\n\n# Useful with built-in functions\nnumbers = [3, 1, 4, 1, 5]\nsorted_nums = sorted(numbers, key=lambda x: -x)\nprint(sorted_nums)  # [5, 4, 3, 1, 1]</code></pre>");

            // ═══════════════════════════════════════════════════════════════
            // COURSE 3: Machine Learning
            // ═══════════════════════════════════════════════════════════════
            Course ml = new Course();
            ml.setTitle("Machine Learning Fundamentals");
            ml.setDescription("Understand core machine learning concepts: supervised and unsupervised learning, regression, classification, and model evaluation techniques.");
            ml.setCategory("Data Science");
            ml.setDifficultyLevel("Intermediate");
            ml.setEstimatedHours(15);
            ml.setTotalLessons(6);
            courseRepository.save(ml);

            saveLesson(lessonRepository, ml, 1, "What is Machine Learning?",
                "<h2>Introduction to Machine Learning</h2>" +
                "<p>Machine Learning (ML) is a subset of Artificial Intelligence (AI) that enables systems to <strong>learn from data</strong> and improve their performance without being explicitly programmed. Instead of writing rules manually, you provide data and let algorithms discover patterns.</p>" +
                "<h3>Traditional Programming vs Machine Learning</h3>" +
                "<ul>" +
                "<li><strong>Traditional Programming:</strong> Input + Rules → Output</li>" +
                "<li><strong>Machine Learning:</strong> Input + Output → Rules (learned automatically)</li>" +
                "</ul>" +
                "<h3>Real-World Examples</h3>" +
                "<ul>" +
                "<li><strong>Email Spam Filtering</strong> — The system learns to identify spam by analyzing thousands of spam and non-spam emails.</li>" +
                "<li><strong>Product Recommendations</strong> — Netflix, Amazon suggest products based on your past behavior.</li>" +
                "<li><strong>Image Recognition</strong> — Identifying faces in photos, detecting objects in self-driving cars.</li>" +
                "<li><strong>Voice Assistants</strong> — Siri, Alexa understand spoken language using ML models.</li>" +
                "<li><strong>Medical Diagnosis</strong> — Detecting diseases from medical images or patient data.</li>" +
                "</ul>" +
                "<h3>Key Terminology</h3>" +
                "<ul>" +
                "<li><strong>Dataset</strong> — A collection of data used for training and testing.</li>" +
                "<li><strong>Features</strong> — The input variables (columns) used for prediction (e.g., age, income).</li>" +
                "<li><strong>Label/Target</strong> — The output variable you want to predict (e.g., price, category).</li>" +
                "<li><strong>Model</strong> — The mathematical representation learned from data.</li>" +
                "<li><strong>Training</strong> — The process of feeding data to an algorithm so it can learn patterns.</li>" +
                "<li><strong>Prediction/Inference</strong> — Using a trained model on new data to get results.</li>" +
                "</ul>");

            saveLesson(lessonRepository, ml, 2, "Types of Machine Learning",
                "<h2>Categories of Machine Learning</h2>" +
                "<h3>1. Supervised Learning</h3>" +
                "<p>The algorithm learns from <strong>labeled data</strong> — each training example has an input and a known correct output.</p>" +
                "<ul>" +
                "<li><strong>Regression</strong> — Predicting continuous values. Example: predicting house prices based on size, location, and age.</li>" +
                "<li><strong>Classification</strong> — Predicting categories. Example: is an email spam or not spam?</li>" +
                "</ul>" +
                "<p><strong>Common Algorithms:</strong> Linear Regression, Logistic Regression, Decision Trees, Random Forest, Support Vector Machines (SVM), K-Nearest Neighbors (KNN).</p>" +
                "<h3>2. Unsupervised Learning</h3>" +
                "<p>The algorithm works with <strong>unlabeled data</strong> — it finds hidden patterns and structures on its own.</p>" +
                "<ul>" +
                "<li><strong>Clustering</strong> — Grouping similar data points. Example: customer segmentation for marketing.</li>" +
                "<li><strong>Dimensionality Reduction</strong> — Reducing the number of features while preserving information. Example: PCA (Principal Component Analysis).</li>" +
                "</ul>" +
                "<p><strong>Common Algorithms:</strong> K-Means, DBSCAN, Hierarchical Clustering, PCA.</p>" +
                "<h3>3. Reinforcement Learning</h3>" +
                "<p>An agent learns by <strong>interacting with an environment</strong> and receiving rewards or penalties for its actions.</p>" +
                "<ul>" +
                "<li>The agent tries different actions and learns which sequences produce the best outcomes.</li>" +
                "<li><strong>Examples:</strong> Game-playing AI (Chess, Go), robotic control, autonomous vehicles.</li>" +
                "</ul>" +
                "<h3>Choosing the Right Type</h3>" +
                "<table>" +
                "<tr><th>Question</th><th>Type</th></tr>" +
                "<tr><td>Do you have labeled data?</td><td>Supervised Learning</td></tr>" +
                "<tr><td>Do you want to discover hidden patterns?</td><td>Unsupervised Learning</td></tr>" +
                "<tr><td>Does the system learn by trial and error?</td><td>Reinforcement Learning</td></tr>" +
                "</table>");

            saveLesson(lessonRepository, ml, 3, "Data Preprocessing",
                "<h2>Preparing Data for Machine Learning</h2>" +
                "<p>Raw data is often messy, incomplete, and inconsistent. <strong>Data preprocessing</strong> transforms raw data into a clean, usable format for ML algorithms.</p>" +
                "<h3>Steps in Data Preprocessing</h3>" +
                "<h3>1. Handling Missing Values</h3>" +
                "<p>Missing values can cause errors. Common strategies:</p>" +
                "<ul>" +
                "<li><strong>Remove</strong> rows or columns with missing values (if few).</li>" +
                "<li><strong>Fill</strong> with mean, median, or mode (imputation).</li>" +
                "<li><strong>Forward/Backward fill</strong> for time series data.</li>" +
                "</ul>" +
                "<pre><code># Python example with Pandas\nimport pandas as pd\n\ndf = pd.read_csv('data.csv')\n\n# Check for missing values\nprint(df.isnull().sum())\n\n# Fill missing values with mean\ndf['age'].fillna(df['age'].mean(), inplace=True)\n\n# Drop rows with any missing value\ndf.dropna(inplace=True)</code></pre>" +
                "<h3>2. Feature Scaling</h3>" +
                "<p>Different features may have different scales (e.g., age 0-100, salary 20000-200000). Scaling brings all features to a similar range.</p>" +
                "<ul>" +
                "<li><strong>Normalization (Min-Max Scaling)</strong> — Scale values to [0, 1].</li>" +
                "<li><strong>Standardization (Z-score)</strong> — Scale values to have mean=0, std=1.</li>" +
                "</ul>" +
                "<pre><code>from sklearn.preprocessing import StandardScaler\n\nscaler = StandardScaler()\ndf[['age', 'salary']] = scaler.fit_transform(df[['age', 'salary']])</code></pre>" +
                "<h3>3. Encoding Categorical Variables</h3>" +
                "<p>ML algorithms work with numbers. Convert text categories to numbers:</p>" +
                "<pre><code># Label Encoding: cat=0, dog=1\nfrom sklearn.preprocessing import LabelEncoder\nle = LabelEncoder()\ndf['animal'] = le.fit_transform(df['animal'])\n\n# One-Hot Encoding: separate binary column per category\ndf = pd.get_dummies(df, columns=['color'])</code></pre>" +
                "<h3>4. Train-Test Split</h3>" +
                "<p>Split data into training set (to learn from) and test set (to evaluate performance):</p>" +
                "<pre><code>from sklearn.model_selection import train_test_split\n\nX_train, X_test, y_train, y_test = train_test_split(\n    X, y, test_size=0.2, random_state=42\n)  # 80% train, 20% test</code></pre>");

            saveLesson(lessonRepository, ml, 4, "Linear Regression",
                "<h2>Understanding Linear Regression</h2>" +
                "<p>Linear Regression is the simplest supervised learning algorithm for <strong>predicting continuous values</strong>. It finds the best-fitting straight line through the data points.</p>" +
                "<h3>The Equation</h3>" +
                "<p>For a single feature: <strong>y = mx + b</strong></p>" +
                "<ul>" +
                "<li><strong>y</strong> — predicted value (dependent variable)</li>" +
                "<li><strong>x</strong> — input feature (independent variable)</li>" +
                "<li><strong>m</strong> — slope (weight) — how much y changes for each unit of x</li>" +
                "<li><strong>b</strong> — intercept — the value of y when x = 0</li>" +
                "</ul>" +
                "<p>For multiple features: <strong>y = w1x1 + w2x2 + ... + wnxn + b</strong></p>" +
                "<h3>How It Works</h3>" +
                "<ol>" +
                "<li>Start with random values for weights and bias.</li>" +
                "<li>Calculate predictions for all training data.</li>" +
                "<li>Measure the error — how far predictions are from actual values.</li>" +
                "<li>Adjust weights to minimize the error (using Gradient Descent).</li>" +
                "<li>Repeat until the error is minimized.</li>" +
                "</ol>" +
                "<h3>Cost Function (Mean Squared Error)</h3>" +
                "<p>MSE = (1/n) * sum of (actual - predicted)^2. The goal is to minimize this value.</p>" +
                "<h3>Implementation Example</h3>" +
                "<pre><code>from sklearn.linear_model import LinearRegression\nfrom sklearn.model_selection import train_test_split\nimport numpy as np\n\n# Sample: predict salary from years of experience\nX = np.array([1, 2, 3, 4, 5, 6, 7, 8]).reshape(-1, 1)\ny = np.array([30000, 35000, 40000, 48000, 55000, 60000, 68000, 75000])\n\nX_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)\n\nmodel = LinearRegression()\nmodel.fit(X_train, y_train)  # Train\n\npredictions = model.predict(X_test)  # Predict\nprint(f\"Slope: {model.coef_[0]:.2f}\")\nprint(f\"Intercept: {model.intercept_:.2f}\")\nprint(f\"R-squared: {model.score(X_test, y_test):.4f}\")</code></pre>" +
                "<h3>When to Use Linear Regression</h3>" +
                "<ul>" +
                "<li>Predicting house prices, stock prices, temperatures</li>" +
                "<li>When the relationship between variables is approximately linear</li>" +
                "<li>When you need an interpretable model</li>" +
                "</ul>");

            saveLesson(lessonRepository, ml, 5, "Classification Algorithms",
                "<h2>Predicting Categories</h2>" +
                "<p>Classification is a supervised learning task where the goal is to predict a <strong>discrete category/class</strong> (e.g., spam/not-spam, cat/dog, disease/healthy).</p>" +
                "<h3>Logistic Regression</h3>" +
                "<p>Despite its name, Logistic Regression is used for <strong>classification</strong>. It uses a sigmoid function to output probabilities between 0 and 1.</p>" +
                "<pre><code>from sklearn.linear_model import LogisticRegression\n\nmodel = LogisticRegression()\nmodel.fit(X_train, y_train)\n\npredictions = model.predict(X_test)\nprint(f\"Accuracy: {model.score(X_test, y_test):.2%}\")</code></pre>" +
                "<h3>Decision Trees</h3>" +
                "<p>A Decision Tree makes decisions by splitting data based on feature values, creating a tree-like structure of if-else rules.</p>" +
                "<ul>" +
                "<li>Easy to understand and visualize</li>" +
                "<li>Can handle both numerical and categorical data</li>" +
                "<li>Prone to overfitting if too deep</li>" +
                "</ul>" +
                "<pre><code>from sklearn.tree import DecisionTreeClassifier\n\nmodel = DecisionTreeClassifier(max_depth=5)\nmodel.fit(X_train, y_train)\nprint(f\"Accuracy: {model.score(X_test, y_test):.2%}\")</code></pre>" +
                "<h3>K-Nearest Neighbors (KNN)</h3>" +
                "<p>KNN classifies a data point based on the majority class of its K closest neighbors in the feature space.</p>" +
                "<pre><code>from sklearn.neighbors import KNeighborsClassifier\n\nmodel = KNeighborsClassifier(n_neighbors=5)\nmodel.fit(X_train, y_train)\nprint(f\"Accuracy: {model.score(X_test, y_test):.2%}\")</code></pre>" +
                "<h3>Random Forest</h3>" +
                "<p>An ensemble of multiple decision trees. Each tree votes, and the majority vote wins. More accurate and less prone to overfitting than a single tree.</p>" +
                "<pre><code>from sklearn.ensemble import RandomForestClassifier\n\nmodel = RandomForestClassifier(n_estimators=100)\nmodel.fit(X_train, y_train)\nprint(f\"Accuracy: {model.score(X_test, y_test):.2%}\")</code></pre>");

            saveLesson(lessonRepository, ml, 6, "Model Evaluation",
                "<h2>Measuring Model Performance</h2>" +
                "<p>A model is only useful if it performs well on <strong>unseen data</strong>. Evaluation metrics tell you how good your model is.</p>" +
                "<h3>For Classification</h3>" +
                "<h4>Confusion Matrix</h4>" +
                "<p>A table showing True Positives (TP), True Negatives (TN), False Positives (FP), and False Negatives (FN).</p>" +
                "<pre><code>from sklearn.metrics import confusion_matrix, classification_report\n\ny_pred = model.predict(X_test)\nprint(confusion_matrix(y_test, y_pred))\nprint(classification_report(y_test, y_pred))</code></pre>" +
                "<h4>Key Metrics</h4>" +
                "<ul>" +
                "<li><strong>Accuracy</strong> = (TP + TN) / Total — Overall correctness.</li>" +
                "<li><strong>Precision</strong> = TP / (TP + FP) — Of predicted positives, how many are correct?</li>" +
                "<li><strong>Recall</strong> = TP / (TP + FN) — Of actual positives, how many did we catch?</li>" +
                "<li><strong>F1-Score</strong> = 2 * (Precision * Recall) / (Precision + Recall) — Balance of precision and recall.</li>" +
                "</ul>" +
                "<h3>For Regression</h3>" +
                "<ul>" +
                "<li><strong>MSE (Mean Squared Error)</strong> — Average of squared differences. Lower is better.</li>" +
                "<li><strong>MAE (Mean Absolute Error)</strong> — Average of absolute differences.</li>" +
                "<li><strong>R-squared (R²)</strong> — Proportion of variance explained by the model. Ranges from 0 to 1; closer to 1 is better.</li>" +
                "</ul>" +
                "<pre><code>from sklearn.metrics import mean_squared_error, r2_score\n\nmse = mean_squared_error(y_test, y_pred)\nr2 = r2_score(y_test, y_pred)\nprint(f\"MSE: {mse:.2f}\")\nprint(f\"R-squared: {r2:.4f}\")</code></pre>" +
                "<h3>Overfitting vs Underfitting</h3>" +
                "<ul>" +
                "<li><strong>Overfitting</strong> — Model performs well on training data but poorly on test data. It memorized the noise.</li>" +
                "<li><strong>Underfitting</strong> — Model performs poorly on both. It is too simple to capture patterns.</li>" +
                "</ul>" +
                "<h3>Cross-Validation</h3>" +
                "<p>Instead of a single train/test split, divide data into K folds and train/test K times. This gives a more reliable estimate.</p>" +
                "<pre><code>from sklearn.model_selection import cross_val_score\n\nscores = cross_val_score(model, X, y, cv=5)\nprint(f\"Average Accuracy: {scores.mean():.2%}\")</code></pre>");

            // ═══════════════════════════════════════════════════════════════
            // COURSE 4: ReactJS
            // ═══════════════════════════════════════════════════════════════
            Course react = new Course();
            react.setTitle("ReactJS Development");
            react.setDescription("Build modern web UIs with React. Learn components, JSX, props, state, event handling and hooks step by step.");
            react.setCategory("Web Development");
            react.setDifficultyLevel("Intermediate");
            react.setEstimatedHours(14);
            react.setTotalLessons(6);
            courseRepository.save(react);

            saveLesson(lessonRepository, react, 1, "Introduction to React",
                "<h2>What is React?</h2>" +
                "<p>React is a <strong>JavaScript library</strong> for building user interfaces, created by Facebook (Meta) in 2013. It focuses on building fast, interactive UIs using a component-based architecture.</p>" +
                "<h3>Why React?</h3>" +
                "<ul>" +
                "<li><strong>Component-Based</strong> — Build encapsulated components that manage their own state, then compose them to make complex UIs.</li>" +
                "<li><strong>Virtual DOM</strong> — React uses a virtual copy of the DOM and updates only what changed, making rendering very fast.</li>" +
                "<li><strong>Declarative</strong> — You describe what the UI should look like, and React handles the updates.</li>" +
                "<li><strong>Large Ecosystem</strong> — Rich community, thousands of packages (React Router, Redux, Material UI).</li>" +
                "<li><strong>Reusable Components</strong> — Write once, use everywhere. Components can be shared across pages or projects.</li>" +
                "</ul>" +
                "<h3>How React Works</h3>" +
                "<ol>" +
                "<li>You write components using JavaScript and JSX (HTML-like syntax).</li>" +
                "<li>React creates a Virtual DOM — a lightweight copy of the real DOM.</li>" +
                "<li>When data changes, React compares the new Virtual DOM with the old one (diffing).</li>" +
                "<li>Only the parts that actually changed are updated in the real DOM (reconciliation).</li>" +
                "</ol>" +
                "<h3>Setting Up a React Project</h3>" +
                "<pre><code># Create a new React app\nnpx create-react-app my-app\ncd my-app\nnpm start\n\n# Project structure:\n# src/\n#   App.js      — Main component\n#   index.js    — Entry point\n#   App.css     — Styles</code></pre>" +
                "<h3>React vs Other Frameworks</h3>" +
                "<ul>" +
                "<li><strong>React</strong> — Library (flexible, choose your own tools)</li>" +
                "<li><strong>Angular</strong> — Full framework (opinionated, batteries included)</li>" +
                "<li><strong>Vue</strong> — Progressive framework (easy to adopt incrementally)</li>" +
                "</ul>");

            saveLesson(lessonRepository, react, 2, "JSX and Rendering",
                "<h2>Understanding JSX</h2>" +
                "<p>JSX (JavaScript XML) is a syntax extension for JavaScript that lets you write HTML-like code in your JavaScript files. It is not valid JavaScript — React's build tools (Babel) transform it into regular function calls.</p>" +
                "<pre><code>// JSX\nconst element = &lt;h1&gt;Hello, World!&lt;/h1&gt;;\n\n// What it compiles to:\nconst element = React.createElement('h1', null, 'Hello, World!');</code></pre>" +
                "<h3>JSX Rules</h3>" +
                "<ul>" +
                "<li>Must return a <strong>single parent element</strong> (or use Fragments <code>&lt;&gt;...&lt;/&gt;</code>).</li>" +
                "<li>Use <code>className</code> instead of <code>class</code> (since <code>class</code> is a reserved word in JS).</li>" +
                "<li>All tags must be closed: <code>&lt;img /&gt;</code>, <code>&lt;br /&gt;</code>.</li>" +
                "<li>Use curly braces <code>{ }</code> to embed JavaScript expressions.</li>" +
                "</ul>" +
                "<h3>Embedding Expressions</h3>" +
                "<pre><code>const name = \"Alice\";\nconst age = 25;\n\nfunction App() {\n  return (\n    &lt;div&gt;\n      &lt;h1&gt;Hello, {name}!&lt;/h1&gt;\n      &lt;p&gt;You are {age} years old.&lt;/p&gt;\n      &lt;p&gt;Next year you'll be {age + 1}.&lt;/p&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>Conditional Rendering</h3>" +
                "<pre><code>function Greeting({ isLoggedIn }) {\n  return (\n    &lt;div&gt;\n      {isLoggedIn ? (\n        &lt;h1&gt;Welcome back!&lt;/h1&gt;\n      ) : (\n        &lt;h1&gt;Please sign in.&lt;/h1&gt;\n      )}\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>Rendering Lists</h3>" +
                "<pre><code>function FruitList() {\n  const fruits = [\"Apple\", \"Banana\", \"Cherry\"];\n  return (\n    &lt;ul&gt;\n      {fruits.map((fruit, index) =&gt; (\n        &lt;li key={index}&gt;{fruit}&lt;/li&gt;\n      ))}\n    &lt;/ul&gt;\n  );\n}</code></pre>");

            saveLesson(lessonRepository, react, 3, "Components and Props",
                "<h2>React Components</h2>" +
                "<p>Components are the building blocks of a React application. Each component is a self-contained piece of UI that can be reused throughout your app.</p>" +
                "<h3>Function Components</h3>" +
                "<p>The modern and recommended way to create components:</p>" +
                "<pre><code>function Welcome() {\n  return &lt;h1&gt;Hello, World!&lt;/h1&gt;;\n}\n\n// Arrow function style\nconst Welcome = () =&gt; {\n  return &lt;h1&gt;Hello, World!&lt;/h1&gt;;\n};</code></pre>" +
                "<h3>Using Components</h3>" +
                "<pre><code>function App() {\n  return (\n    &lt;div&gt;\n      &lt;Welcome /&gt;\n      &lt;Welcome /&gt;\n      &lt;Welcome /&gt;\n    &lt;/div&gt;\n  );\n}\n// Renders \"Hello, World!\" three times</code></pre>" +
                "<h3>Props (Properties)</h3>" +
                "<p>Props let you pass data from a parent component to a child component. They are read-only.</p>" +
                "<pre><code>function Welcome({ name, role }) {\n  return (\n    &lt;div&gt;\n      &lt;h1&gt;Hello, {name}!&lt;/h1&gt;\n      &lt;p&gt;Role: {role}&lt;/p&gt;\n    &lt;/div&gt;\n  );\n}\n\nfunction App() {\n  return (\n    &lt;div&gt;\n      &lt;Welcome name=\"Alice\" role=\"Developer\" /&gt;\n      &lt;Welcome name=\"Bob\" role=\"Designer\" /&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>Default Props</h3>" +
                "<pre><code>function Button({ text = \"Click Me\", color = \"blue\" }) {\n  return (\n    &lt;button style={{ backgroundColor: color }}&gt;\n      {text}\n    &lt;/button&gt;\n  );\n}\n\n// &lt;Button /&gt;  → uses defaults\n// &lt;Button text=\"Submit\" color=\"green\" /&gt;</code></pre>" +
                "<h3>Children Props</h3>" +
                "<pre><code>function Card({ children, title }) {\n  return (\n    &lt;div className=\"card\"&gt;\n      &lt;h2&gt;{title}&lt;/h2&gt;\n      &lt;div&gt;{children}&lt;/div&gt;\n    &lt;/div&gt;\n  );\n}\n\n// Usage:\n&lt;Card title=\"My Card\"&gt;\n  &lt;p&gt;This content goes inside the card.&lt;/p&gt;\n&lt;/Card&gt;</code></pre>");

            saveLesson(lessonRepository, react, 4, "State Management",
                "<h2>Component State</h2>" +
                "<p>State is data that a component manages internally. Unlike props, state can change over time, and when state changes, the component re-renders.</p>" +
                "<h3>useState Hook</h3>" +
                "<pre><code>import { useState } from 'react';\n\nfunction Counter() {\n  const [count, setCount] = useState(0);\n\n  return (\n    &lt;div&gt;\n      &lt;p&gt;Count: {count}&lt;/p&gt;\n      &lt;button onClick={() =&gt; setCount(count + 1)}&gt;+1&lt;/button&gt;\n      &lt;button onClick={() =&gt; setCount(count - 1)}&gt;-1&lt;/button&gt;\n      &lt;button onClick={() =&gt; setCount(0)}&gt;Reset&lt;/button&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>How useState Works</h3>" +
                "<ul>" +
                "<li><code>useState(initialValue)</code> returns an array with two items: the current value and a setter function.</li>" +
                "<li>When you call the setter (e.g., <code>setCount</code>), React re-renders the component with the new value.</li>" +
                "<li>Never modify state directly — always use the setter function.</li>" +
                "</ul>" +
                "<h3>State with Objects</h3>" +
                "<pre><code>function UserForm() {\n  const [user, setUser] = useState({ name: '', email: '' });\n\n  const handleChange = (e) =&gt; {\n    setUser({\n      ...user,  // spread existing values\n      [e.target.name]: e.target.value  // update one field\n    });\n  };\n\n  return (\n    &lt;form&gt;\n      &lt;input name=\"name\" value={user.name} onChange={handleChange} /&gt;\n      &lt;input name=\"email\" value={user.email} onChange={handleChange} /&gt;\n      &lt;p&gt;{user.name} — {user.email}&lt;/p&gt;\n    &lt;/form&gt;\n  );\n}</code></pre>" +
                "<h3>State with Arrays</h3>" +
                "<pre><code>function TodoList() {\n  const [todos, setTodos] = useState([]);\n  const [input, setInput] = useState('');\n\n  const addTodo = () =&gt; {\n    setTodos([...todos, input]); // add item\n    setInput('');\n  };\n\n  const removeTodo = (index) =&gt; {\n    setTodos(todos.filter((_, i) =&gt; i !== index)); // remove\n  };\n\n  return (\n    &lt;div&gt;\n      &lt;input value={input} onChange={(e) =&gt; setInput(e.target.value)} /&gt;\n      &lt;button onClick={addTodo}&gt;Add&lt;/button&gt;\n      &lt;ul&gt;\n        {todos.map((todo, i) =&gt; (\n          &lt;li key={i}&gt;{todo} &lt;button onClick={() =&gt; removeTodo(i)}&gt;X&lt;/button&gt;&lt;/li&gt;\n        ))}\n      &lt;/ul&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>");

            saveLesson(lessonRepository, react, 5, "Event Handling",
                "<h2>Handling Events in React</h2>" +
                "<p>React handles events similarly to HTML but with some differences: event names use camelCase, and you pass functions rather than strings.</p>" +
                "<h3>Basic Click Events</h3>" +
                "<pre><code>function App() {\n  const handleClick = () =&gt; {\n    alert('Button clicked!');\n  };\n\n  return &lt;button onClick={handleClick}&gt;Click Me&lt;/button&gt;;\n}\n\n// Inline handler\n&lt;button onClick={() =&gt; alert('Clicked!')}&gt;Click&lt;/button&gt;</code></pre>" +
                "<h3>Event Object</h3>" +
                "<pre><code>function App() {\n  const handleClick = (event) =&gt; {\n    console.log('Button text:', event.target.textContent);\n    console.log('Event type:', event.type);\n  };\n\n  return &lt;button onClick={handleClick}&gt;Click Me&lt;/button&gt;;\n}</code></pre>" +
                "<h3>Form Events</h3>" +
                "<pre><code>function LoginForm() {\n  const [email, setEmail] = useState('');\n  const [password, setPassword] = useState('');\n\n  const handleSubmit = (e) =&gt; {\n    e.preventDefault(); // Prevent page reload\n    console.log('Login:', email, password);\n  };\n\n  return (\n    &lt;form onSubmit={handleSubmit}&gt;\n      &lt;input\n        type=\"email\"\n        value={email}\n        onChange={(e) =&gt; setEmail(e.target.value)}\n        placeholder=\"Email\"\n      /&gt;\n      &lt;input\n        type=\"password\"\n        value={password}\n        onChange={(e) =&gt; setPassword(e.target.value)}\n        placeholder=\"Password\"\n      /&gt;\n      &lt;button type=\"submit\"&gt;Login&lt;/button&gt;\n    &lt;/form&gt;\n  );\n}</code></pre>" +
                "<h3>Passing Arguments to Handlers</h3>" +
                "<pre><code>function App() {\n  const handleDelete = (id) =&gt; {\n    console.log('Deleting item:', id);\n  };\n\n  return (\n    &lt;div&gt;\n      &lt;button onClick={() =&gt; handleDelete(1)}&gt;Delete Item 1&lt;/button&gt;\n      &lt;button onClick={() =&gt; handleDelete(2)}&gt;Delete Item 2&lt;/button&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>Common Events</h3>" +
                "<ul>" +
                "<li><code>onClick</code> — Mouse click</li>" +
                "<li><code>onChange</code> — Input value changed</li>" +
                "<li><code>onSubmit</code> — Form submitted</li>" +
                "<li><code>onMouseEnter / onMouseLeave</code> — Hover</li>" +
                "<li><code>onKeyDown / onKeyUp</code> — Keyboard</li>" +
                "<li><code>onFocus / onBlur</code> — Focus gained/lost</li>" +
                "</ul>");

            saveLesson(lessonRepository, react, 6, "React Hooks",
                "<h2>Essential React Hooks</h2>" +
                "<p>Hooks are functions that let you use React features (state, lifecycle, context) in function components. They were introduced in React 16.8.</p>" +
                "<h3>useState (Recap)</h3>" +
                "<pre><code>const [value, setValue] = useState(initialValue);</code></pre>" +
                "<h3>useEffect — Side Effects</h3>" +
                "<p><code>useEffect</code> runs code after the component renders. It replaces lifecycle methods like <code>componentDidMount</code> and <code>componentDidUpdate</code>.</p>" +
                "<pre><code>import { useState, useEffect } from 'react';\n\nfunction Timer() {\n  const [seconds, setSeconds] = useState(0);\n\n  useEffect(() =&gt; {\n    const interval = setInterval(() =&gt; {\n      setSeconds(s =&gt; s + 1);\n    }, 1000);\n\n    // Cleanup function (runs on unmount)\n    return () =&gt; clearInterval(interval);\n  }, []); // Empty array = run only once on mount\n\n  return &lt;p&gt;Seconds: {seconds}&lt;/p&gt;;\n}</code></pre>" +
                "<h3>useEffect Dependency Array</h3>" +
                "<ul>" +
                "<li><code>useEffect(() =&gt; {}, [])</code> — Runs once on mount (like componentDidMount).</li>" +
                "<li><code>useEffect(() =&gt; {})</code> — Runs after every render.</li>" +
                "<li><code>useEffect(() =&gt; {}, [value])</code> — Runs when <code>value</code> changes.</li>" +
                "</ul>" +
                "<h3>Fetching Data with useEffect</h3>" +
                "<pre><code>function UserList() {\n  const [users, setUsers] = useState([]);\n  const [loading, setLoading] = useState(true);\n\n  useEffect(() =&gt; {\n    fetch('/api/users')\n      .then(res =&gt; res.json())\n      .then(data =&gt; {\n        setUsers(data);\n        setLoading(false);\n      });\n  }, []);\n\n  if (loading) return &lt;p&gt;Loading...&lt;/p&gt;;\n  return (\n    &lt;ul&gt;\n      {users.map(u =&gt; &lt;li key={u.id}&gt;{u.name}&lt;/li&gt;)}\n    &lt;/ul&gt;\n  );\n}</code></pre>" +
                "<h3>useRef — Persistent References</h3>" +
                "<pre><code>import { useRef } from 'react';\n\nfunction TextInput() {\n  const inputRef = useRef(null);\n\n  const focusInput = () =&gt; inputRef.current.focus();\n\n  return (\n    &lt;div&gt;\n      &lt;input ref={inputRef} /&gt;\n      &lt;button onClick={focusInput}&gt;Focus Input&lt;/button&gt;\n    &lt;/div&gt;\n  );\n}</code></pre>" +
                "<h3>Rules of Hooks</h3>" +
                "<ul>" +
                "<li>Only call Hooks at the <strong>top level</strong> — not inside loops, conditions, or nested functions.</li>" +
                "<li>Only call Hooks from <strong>React function components</strong> or custom Hooks.</li>" +
                "</ul>");

            // ═══════════════════════════════════════════════════════════════
            // COURSE 5: MySQL
            // ═══════════════════════════════════════════════════════════════
            Course mysql = new Course();
            mysql.setTitle("MySQL Database");
            mysql.setDescription("Learn relational database fundamentals with MySQL. Master creating databases, tables, queries, filtering, sorting, and JOIN operations.");
            mysql.setCategory("Database");
            mysql.setDifficultyLevel("Beginner");
            mysql.setEstimatedHours(10);
            mysql.setTotalLessons(6);
            courseRepository.save(mysql);

            saveLesson(lessonRepository, mysql, 1, "Introduction to MySQL",
                "<h2>What is MySQL?</h2>" +
                "<p>MySQL is an open-source <strong>Relational Database Management System (RDBMS)</strong> that uses Structured Query Language (SQL) to manage data. It is one of the most popular databases in the world, powering websites and applications for companies like Facebook, Twitter, and YouTube.</p>" +
                "<h3>Key Concepts</h3>" +
                "<ul>" +
                "<li><strong>Database</strong> — An organized collection of structured data.</li>" +
                "<li><strong>Table</strong> — Data is stored in tables with rows and columns, similar to a spreadsheet.</li>" +
                "<li><strong>Row (Record)</strong> — A single entry in a table (e.g., one student).</li>" +
                "<li><strong>Column (Field)</strong> — A specific attribute (e.g., name, age, email).</li>" +
                "<li><strong>Primary Key</strong> — A unique identifier for each row (e.g., student_id).</li>" +
                "<li><strong>Foreign Key</strong> — A column that references the primary key of another table, creating a relationship.</li>" +
                "</ul>" +
                "<h3>SQL Categories</h3>" +
                "<table>" +
                "<tr><th>Category</th><th>Purpose</th><th>Commands</th></tr>" +
                "<tr><td><strong>DDL</strong> (Data Definition)</td><td>Define/modify structure</td><td>CREATE, ALTER, DROP, TRUNCATE</td></tr>" +
                "<tr><td><strong>DML</strong> (Data Manipulation)</td><td>Manipulate data</td><td>INSERT, UPDATE, DELETE</td></tr>" +
                "<tr><td><strong>DQL</strong> (Data Query)</td><td>Query/retrieve data</td><td>SELECT</td></tr>" +
                "<tr><td><strong>DCL</strong> (Data Control)</td><td>Permissions</td><td>GRANT, REVOKE</td></tr>" +
                "</table>" +
                "<h3>Why MySQL?</h3>" +
                "<ul>" +
                "<li>Free and open-source</li>" +
                "<li>Fast, reliable, and scalable</li>" +
                "<li>Cross-platform (runs on Windows, Linux, macOS)</li>" +
                "<li>Widely supported by programming languages (Java, Python, PHP, Node.js)</li>" +
                "<li>Large community and extensive documentation</li>" +
                "</ul>");

            saveLesson(lessonRepository, mysql, 2, "Creating Databases and Tables",
                "<h2>Database and Table Operations</h2>" +
                "<h3>Creating a Database</h3>" +
                "<pre><code>-- Create a new database\nCREATE DATABASE school;\n\n-- Show all databases\nSHOW DATABASES;\n\n-- Select a database to use\nUSE school;\n\n-- Delete a database\nDROP DATABASE school;</code></pre>" +
                "<h3>Creating Tables</h3>" +
                "<pre><code>CREATE TABLE students (\n    id INT AUTO_INCREMENT PRIMARY KEY,\n    first_name VARCHAR(50) NOT NULL,\n    last_name VARCHAR(50) NOT NULL,\n    email VARCHAR(100) UNIQUE,\n    age INT,\n    enrollment_date DATE DEFAULT (CURRENT_DATE)\n);</code></pre>" +
                "<h3>Common Data Types</h3>" +
                "<table>" +
                "<tr><th>Type</th><th>Description</th><th>Example</th></tr>" +
                "<tr><td><code>INT</code></td><td>Whole number</td><td>42</td></tr>" +
                "<tr><td><code>DECIMAL(p,s)</code></td><td>Fixed-point decimal</td><td>99.99</td></tr>" +
                "<tr><td><code>VARCHAR(n)</code></td><td>Variable-length string (max n chars)</td><td>'Hello'</td></tr>" +
                "<tr><td><code>TEXT</code></td><td>Long text</td><td>Paragraphs of text</td></tr>" +
                "<tr><td><code>DATE</code></td><td>Date (YYYY-MM-DD)</td><td>'2024-01-15'</td></tr>" +
                "<tr><td><code>DATETIME</code></td><td>Date and time</td><td>'2024-01-15 10:30:00'</td></tr>" +
                "<tr><td><code>BOOLEAN</code></td><td>True/False (stored as 1/0)</td><td>TRUE</td></tr>" +
                "</table>" +
                "<h3>Constraints</h3>" +
                "<ul>" +
                "<li><code>PRIMARY KEY</code> — Unique identifier for each row</li>" +
                "<li><code>NOT NULL</code> — Column cannot be empty</li>" +
                "<li><code>UNIQUE</code> — All values must be different</li>" +
                "<li><code>DEFAULT</code> — Provides a default value</li>" +
                "<li><code>AUTO_INCREMENT</code> — Automatically generates sequential numbers</li>" +
                "<li><code>FOREIGN KEY</code> — Links to another table's primary key</li>" +
                "</ul>" +
                "<h3>Modifying Tables</h3>" +
                "<pre><code>-- Add a column\nALTER TABLE students ADD phone VARCHAR(15);\n\n-- Modify a column\nALTER TABLE students MODIFY age INT NOT NULL;\n\n-- Drop a column\nALTER TABLE students DROP COLUMN phone;\n\n-- Show table structure\nDESCRIBE students;</code></pre>");

            saveLesson(lessonRepository, mysql, 3, "INSERT and SELECT Queries",
                "<h2>Adding and Retrieving Data</h2>" +
                "<h3>INSERT — Adding Data</h3>" +
                "<pre><code>-- Insert a single row\nINSERT INTO students (first_name, last_name, email, age)\nVALUES ('Alice', 'Johnson', 'alice@email.com', 20);\n\n-- Insert multiple rows\nINSERT INTO students (first_name, last_name, email, age) VALUES\n    ('Bob', 'Smith', 'bob@email.com', 22),\n    ('Carol', 'Williams', 'carol@email.com', 21),\n    ('David', 'Brown', 'david@email.com', 23),\n    ('Eve', 'Davis', 'eve@email.com', 20);</code></pre>" +
                "<h3>SELECT — Retrieving Data</h3>" +
                "<pre><code>-- Select all columns\nSELECT * FROM students;\n\n-- Select specific columns\nSELECT first_name, last_name, email FROM students;\n\n-- Select with alias\nSELECT first_name AS \"First Name\", last_name AS \"Last Name\"\nFROM students;</code></pre>" +
                "<h3>SELECT DISTINCT</h3>" +
                "<pre><code>-- Get unique values only (no duplicates)\nSELECT DISTINCT age FROM students;</code></pre>" +
                "<h3>LIMIT</h3>" +
                "<pre><code>-- Get first 3 rows\nSELECT * FROM students LIMIT 3;\n\n-- Skip 2, then get 3 (for pagination)\nSELECT * FROM students LIMIT 3 OFFSET 2;</code></pre>" +
                "<h3>Aggregate Functions</h3>" +
                "<pre><code>SELECT COUNT(*) AS total_students FROM students;\nSELECT AVG(age) AS average_age FROM students;\nSELECT MIN(age) AS youngest FROM students;\nSELECT MAX(age) AS oldest FROM students;\nSELECT SUM(age) AS total_age FROM students;</code></pre>");

            saveLesson(lessonRepository, mysql, 4, "UPDATE and DELETE",
                "<h2>Modifying and Removing Data</h2>" +
                "<h3>UPDATE — Modifying Data</h3>" +
                "<pre><code>-- Update a specific row\nUPDATE students\nSET email = 'alice.j@email.com'\nWHERE id = 1;\n\n-- Update multiple columns\nUPDATE students\nSET age = 21, email = 'bob.new@email.com'\nWHERE first_name = 'Bob' AND last_name = 'Smith';\n\n-- Update multiple rows\nUPDATE students\nSET age = age + 1\nWHERE age < 21;</code></pre>" +
                "<p><strong>Warning:</strong> Always use a WHERE clause with UPDATE. Without it, ALL rows will be updated!</p>" +
                "<pre><code>-- DANGEROUS: Updates ALL rows!\nUPDATE students SET age = 25;\n\n-- Safe: Updates only matching rows\nUPDATE students SET age = 25 WHERE id = 3;</code></pre>" +
                "<h3>DELETE — Removing Data</h3>" +
                "<pre><code>-- Delete a specific row\nDELETE FROM students WHERE id = 5;\n\n-- Delete rows matching a condition\nDELETE FROM students WHERE age > 25;\n\n-- Delete all rows (keeps table structure)\nDELETE FROM students;</code></pre>" +
                "<p><strong>Warning:</strong> <code>DELETE</code> without WHERE removes ALL rows!</p>" +
                "<h3>TRUNCATE vs DELETE</h3>" +
                "<ul>" +
                "<li><code>DELETE FROM students;</code> — Removes all rows one by one. Can be rolled back. Auto-increment continues.</li>" +
                "<li><code>TRUNCATE TABLE students;</code> — Drops and recreates the table. Faster. Resets auto-increment. Cannot be rolled back.</li>" +
                "</ul>" +
                "<h3>Safe Updates Mode</h3>" +
                "<p>MySQL has a safe mode that prevents UPDATE/DELETE without WHERE or without a KEY column in WHERE:</p>" +
                "<pre><code>-- Turn off safe mode (not recommended for production)\nSET SQL_SAFE_UPDATES = 0;\n\n-- Turn on safe mode\nSET SQL_SAFE_UPDATES = 1;</code></pre>");

            saveLesson(lessonRepository, mysql, 5, "Filtering and Sorting",
                "<h2>WHERE, ORDER BY, GROUP BY</h2>" +
                "<h3>WHERE — Filtering Rows</h3>" +
                "<pre><code>-- Comparison operators\nSELECT * FROM students WHERE age = 20;\nSELECT * FROM students WHERE age > 20;\nSELECT * FROM students WHERE age != 22;\nSELECT * FROM students WHERE age >= 21;\n\n-- Text comparison\nSELECT * FROM students WHERE first_name = 'Alice';</code></pre>" +
                "<h3>AND, OR, NOT</h3>" +
                "<pre><code>-- AND: both conditions must be true\nSELECT * FROM students\nWHERE age >= 20 AND age <= 25;\n\n-- OR: at least one condition must be true\nSELECT * FROM students\nWHERE first_name = 'Alice' OR first_name = 'Bob';\n\n-- NOT: negation\nSELECT * FROM students\nWHERE NOT age = 20;</code></pre>" +
                "<h3>BETWEEN, IN, LIKE</h3>" +
                "<pre><code>-- BETWEEN: range (inclusive)\nSELECT * FROM students WHERE age BETWEEN 20 AND 23;\n\n-- IN: match any value in a list\nSELECT * FROM students\nWHERE first_name IN ('Alice', 'Bob', 'Carol');\n\n-- LIKE: pattern matching\n-- % matches any sequence of characters\n-- _ matches exactly one character\nSELECT * FROM students WHERE last_name LIKE 'S%';    -- starts with S\nSELECT * FROM students WHERE email LIKE '%@email.com'; -- ends with\nSELECT * FROM students WHERE first_name LIKE '_o%';  -- 2nd char is 'o'</code></pre>" +
                "<h3>ORDER BY — Sorting</h3>" +
                "<pre><code>-- Ascending (default)\nSELECT * FROM students ORDER BY age ASC;\n\n-- Descending\nSELECT * FROM students ORDER BY last_name DESC;\n\n-- Multiple columns\nSELECT * FROM students ORDER BY age DESC, first_name ASC;</code></pre>" +
                "<h3>GROUP BY — Grouping</h3>" +
                "<pre><code>-- Count students per age\nSELECT age, COUNT(*) AS count\nFROM students\nGROUP BY age;\n\n-- HAVING: filter groups (like WHERE for groups)\nSELECT age, COUNT(*) AS count\nFROM students\nGROUP BY age\nHAVING count > 1;</code></pre>");

            saveLesson(lessonRepository, mysql, 6, "JOIN Operations",
                "<h2>Combining Data from Multiple Tables</h2>" +
                "<p>JOINs allow you to retrieve data from two or more related tables based on matching columns (usually primary and foreign keys).</p>" +
                "<h3>Setup: Two Related Tables</h3>" +
                "<pre><code>CREATE TABLE departments (\n    id INT AUTO_INCREMENT PRIMARY KEY,\n    name VARCHAR(50) NOT NULL\n);\n\nCREATE TABLE employees (\n    id INT AUTO_INCREMENT PRIMARY KEY,\n    name VARCHAR(50),\n    salary DECIMAL(10,2),\n    department_id INT,\n    FOREIGN KEY (department_id) REFERENCES departments(id)\n);\n\nINSERT INTO departments VALUES (1,'Engineering'), (2,'Marketing'), (3,'HR');\nINSERT INTO employees VALUES\n    (1,'Alice',75000,1), (2,'Bob',65000,1),\n    (3,'Carol',60000,2), (4,'David',55000,NULL);</code></pre>" +
                "<h3>INNER JOIN</h3>" +
                "<p>Returns only rows that have matching values in both tables:</p>" +
                "<pre><code>SELECT e.name, e.salary, d.name AS department\nFROM employees e\nINNER JOIN departments d ON e.department_id = d.id;\n\n-- Result: Alice-Engineering, Bob-Engineering, Carol-Marketing\n-- David is excluded (no matching department)</code></pre>" +
                "<h3>LEFT JOIN</h3>" +
                "<p>Returns all rows from the left table, plus matching rows from the right. If no match, right side shows NULL:</p>" +
                "<pre><code>SELECT e.name, d.name AS department\nFROM employees e\nLEFT JOIN departments d ON e.department_id = d.id;\n\n-- Result: Alice-Engineering, Bob-Engineering, Carol-Marketing, David-NULL</code></pre>" +
                "<h3>RIGHT JOIN</h3>" +
                "<p>Returns all rows from the right table, plus matching rows from the left:</p>" +
                "<pre><code>SELECT e.name, d.name AS department\nFROM employees e\nRIGHT JOIN departments d ON e.department_id = d.id;\n\n-- Result: Alice-Engineering, Bob-Engineering, Carol-Marketing, NULL-HR</code></pre>" +
                "<h3>Summary of JOINs</h3>" +
                "<table>" +
                "<tr><th>JOIN Type</th><th>Returns</th></tr>" +
                "<tr><td>INNER JOIN</td><td>Only matching rows from both tables</td></tr>" +
                "<tr><td>LEFT JOIN</td><td>All left rows + matching right rows</td></tr>" +
                "<tr><td>RIGHT JOIN</td><td>All right rows + matching left rows</td></tr>" +
                "<tr><td>FULL OUTER JOIN</td><td>All rows from both tables (MySQL uses UNION workaround)</td></tr>" +
                "</table>");
        };
    }

    private void saveLesson(LessonRepository repo, Course course, int order, String title, String content) {
        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setOrderIndex(order);
        lesson.setTitle(title);
        lesson.setContent(content);
        repo.save(lesson);
    }
}
