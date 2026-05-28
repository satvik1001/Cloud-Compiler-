let editor;

const API = window.location.origin;

const templates = {

    java:
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello Java");
    }
},

    python:
print("Hello Python"),

    cpp:
#include<iostream>

using namespace std;

int main(){

    cout<<"Hello C++"<<endl;

    return 0;
}
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

    try {

        const response =
            await fetch(`${API}/history`);

        const data =
            await response.json();

        let historyText = "";

        data.reverse().forEach((sub, index) => {

            historyText +=



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


;
        });

        document.getElementById(
            "output"
        ).innerText = historyText;

    }

    catch (error) {

        document.getElementById(
            "output"
        ).innerText =
            "Failed to load history";
    }
}

async function runCode() {

    try {

        const code =
            editor.getValue();

        const language =
            document.getElementById("language").value;

        const input =
            document.getElementById("input").value;

        const response =
            await fetch(
                `${API}/run`,
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

    catch(error) {

        document.getElementById('output')
                .innerText =
                "Backend connection failed";
    }
}
