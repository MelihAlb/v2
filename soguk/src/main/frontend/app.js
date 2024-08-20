document.addEventListener('DOMContentLoaded', () => {
    // Registration Form
    const registerForm = document.getElementById('register-form');
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nick = document.getElementById('register-nick').value;
        const email = document.getElementById('register-email').value;
        const password = document.getElementById('register-password').value;

        const user = { nick, email, password };

        try {
            const response = await fetch('/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(user)
            });
            const result = await response.json();
            if (response.ok) {
                alert('User registered successfully');
            } else {
                alert(`Error: ${result.message}`);
            }
        } catch (error) {
            alert('An error occurred: ' + error.message);
        }
    });

    // Login Form
    const loginForm = document.getElementById('login-form');
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const nick = document.getElementById('login-nick').value;
        const password = document.getElementById('login-password').value;

        const loginRequest = { nick, password };

        try {
            const response = await fetch('/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginRequest)
            });
            const result = await response.text();
            if (response.ok) {
                alert(result);
            } else {
                alert('Login failed: ' + result);
            }
        } catch (error) {
            alert('An error occurred: ' + error.message);
        }
    });
});
