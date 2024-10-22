async function login() {
  const email = document.getElementById("inputEmailAddress").value;
  const password = document.getElementById("inputPassword").value;

  const loginRequest = {
    username: email,
    password: password,
  };

  try {
    const response = await fetch("/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(loginRequest),
    });

    if (response.ok) {
      const result = await response.text();
      alert("Login successful " + result);
    } else {
      const error = await response.text();
      alert("Login failed: " + error);
    }
  } catch (error) {
    alert("Error: " + error.message);
  }
}
