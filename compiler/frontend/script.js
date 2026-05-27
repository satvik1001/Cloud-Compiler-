let editor;

const templates = {

    java:
`public class Main {

    public static void main(String[] args) {

        System.out.println("Hello Java");
    }
}`,

    python:
`print("Hello Python")`,

    cpp:
`#include<iostream>

using namespace std;

int main(){

    cout<<"Hello C++"<<endl;

    return 0;
}`
};

require.config({
    paths: {
        vs: 'https://cdn.jsdelivr.net/npm/monaco-editor@0.52.2/min/vs'
    }
});

require(['vs/editor/editor.main'], function () {

    editor = monaco.editor.create(
        document.getElementById('editor'),
        {

            value: templates.java,

            language: 'java',

            theme: 'vs-dark',

            automaticLayout: true,

            fontSize: 16
        }
    );
});

function changeLanguage() {

    const language =
        document.getElementById("language").value;

    console.log("Changed Language:", language);

    let monacoLanguage = language;

    if(language === "cpp") {

        monacoLanguage = "cpp";
    }

    monaco.editor.setModelLanguage(
        editor.getModel(),
        monacoLanguage
    );

    editor.setValue(
        templates[language]
    );
}
async function loadHistory() {

    const response =
        await fetch(
            'http://localhost:9090/history'
        );

    const data =
        await response.json();

    console.log(data);

    let output = "";

    data.forEach(sub => {

        output +=

`Language: ${sub.language}

Output: ${sub.output}

Execution Time:
${sub.executionTime} ms

-------------------------

`;
    });

    document.getElementById(
        "output"
    ).innerText = output;
}

async function loadHistory() {

    const response =
        await fetch(
            'http://localhost:9090/history'
        );

    const data =
        await response.json();

    let historyText = "";

    // LATEST TO EARLIEST

    data.reverse().forEach((sub, index) => {

        historyText +=

`================================

Submission #${index + 1}

Language:
${sub.language}

Code:
${sub.code}

Output:
${sub.output}

Execution Time:
${sub.executionTime} ms

================================


`;
    });

    document.getElementById(
        "output"
    ).innerText = historyText;
}

async function runCode() {

    const code =
        editor.getValue();

    const language =
        document.getElementById("language").value;

    const input =
        document.getElementById("input").value;

    console.log("RUNNING:", language);

    const response =
        await fetch(
            'http://localhost:9090/run',
            {

                method: 'POST',

                headers: {
                    'Content-Type': 'application/json'
                },

                body: JSON.stringify({

                    code: code,

                    language: language,

                    input: input
                })
            }
        );

    const result =
        await response.text();

    document.getElementById('output')
            .innerText = result;
}