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