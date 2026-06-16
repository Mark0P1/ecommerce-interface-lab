document
    .getElementById(
        "loginForm"
    )
    .addEventListener(
        "submit",
        async (event) => {

            event.preventDefault();

            const form =
                new FormData(
                    event.target
                );

            const response =
                await fetch(
                    "http://localhost:8080/login",
                    {
                        method: "POST",
                        body: form,
                        credentials: "include"
                    }
                );

            if (
                response.ok
            ) {

                window.location.href =
                    "products.html";
            }
        }
    );

document
    .getElementById(
        "loginForm"
    )
    .addEventListener(
        "submit",
        async event => {

            event.preventDefault();

            const username =
                document.querySelector(
                    "[name=username]"
                ).value;

            const password =
                document.querySelector(
                    "[name=password]"
                ).value;

            const response =
                await fetch(
                    "http://localhost:8080/api/v1/auth/login",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify({
                                username,
                                password
                            })
                    }
                );

            const data =
                await response.json();

            localStorage.setItem(
                "jwt_token",
                data.token
            );

            window.location.href =
                "products.html";
        }
    );