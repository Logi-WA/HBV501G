document.addEventListener('DOMContentLoaded', () => {
  checkAuthenticationStatus();
});

async function checkAuthenticationStatus() {
  try {
    const response = await fetch('/api/users/me', {
      method: 'GET',
      credentials: 'include',
    });

    if (response.ok) {
      // User is logged in
      const userData = await response.json();
      updateNavbarLoggedIn(userData);
    } else {
      updateNavbarLoggedOut();
    }
  } catch (error) {
    console.error('Failed to check authentication status: ' + error);
    updateNavbarLoggedOut();
  }
}

function updateNavbarLoggedIn(userData) {
  const loginButton = document.getElementById('loginButton');
  if (loginButton) {
    loginButton.remove();
  }

  // Create user dropdown
  const navbar = document.getElementById('navbarSupportedContent');

  const dropdownHtml = `
        <div class="nav-item dropdown no-caret dropdown-user">
        <a
          class="btn btn-icon btn-transparent-dark dropdown-toggle"
          id="navbarDropdownUserImage"
          href="#"
          role="button"
          data-bs-toggle="dropdown"
          aria-haspopup="true"
          aria-expanded="false"
          ><img class="img-fluid" src="/img/user.png"
        /></a>
        <div
          class="dropdown-menu dropdown-menu-end border-0 shadow animated--fade-in-up"
          aria-labelledby="navbarDropdownUserImage"
        >
          <h6 class="dropdown-header d-flex align-items-center">
            <img class="dropdown-user-img" src="/img/user.png" />
            <div class="dropdown-user-details">
              <div class="dropdown-user-details-name">User</div>
              <div class="dropdown-user-details-email">email@example.com</div>
            </div>
          </h6>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="/account">
            <div class="dropdown-item-icon">
              <i data-feather="settings"></i>
            </div>
            Account
          </a>
          <a class="dropdown-item" href="#" onclick="logout()">
            <div class="dropdown-item-icon">
              <i data-feather="log-out"></i>
            </div>
            Logout
          </a>
        </div>
      </div>
  `;

  navbar?.insertAdjacentHTML('beforeend', dropdownHtml);
  // Re-initialize Feather icons
  if (window.feather) {
    window.feather.replace();
  }
}

function updateNavbarLoggedOut() {
  // remove user dropdown if exists
  const userDropdown = document.querySelector('.dropdown-user');
  if (userDropdown) {
    userDropdown.remove();
  }

  // Add login button
  const navbar = document.getElementById('navbarSupportedContent');

  const loginButtonHtml = `
        <a
        class="btn fw-500 lift rounded-4 btn-light text-dark"
        htype="button"
        data-bs-toggle="modal"
        data-bs-target="#auth-login"
        href=""
        id="loginButton"
      >
        <i class="fas fa-user"></i>
      </a>
  `;

  navbar?.insertAdjacentHTML('beforeend', loginButtonHtml);
  // Re-initialize Feather icons
  if (window.feather) {
    window.feather.replace();
  }
}

async function login() {
  const email = document.getElementById('inputEmailAddress').value;
  const password = document.getElementById('inputPassword').value;

  const loginRequest = {
    username: email,
    password: password,
  };

  try {
    const response = await fetch('/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(loginRequest),
      credentials: 'include',
    });

    if (response.ok) {
      // Dismiss the modal
      const loginModalElement = document.getElementById('auth-login');
      const modalInstance = bootstrap.Modal.getInstance(loginModalElement);
      if (modalInstance) {
        modalInstance.hide();
      } else {
        // If modal instance is not found, initialize it
        const newModalInstance = new bootstrap.Modal(loginModalElement);
        newModalInstance.hide();
      }

      // Fetch current user data
      const userResponse = await fetch('/api/users/me', {
        method: 'GET',
        credentials: 'include',
      });

      if (userResponse.ok) {
        const userData = await userResponse.json();
        updateNavbarLoggedIn(userData);
      } else {
        console.error('Failed to fetch user data after login.');
      }
    } else {
      const error = await response.text();
      alert('Login failed: ' + error);
    }
  } catch (error) {
    alert('Error: ' + error.message);
  }
}

async function logout() {
  try {
    const response = await fetch('/auth/logout', {
      method: 'POST',
      credentials: 'include',
    });

    if (response.ok) {
      updateNavbarLoggedOut();
    } else {
      console.error('Logout failed.');
    }
  } catch (error) {
    console.error('Error during logout:', error);
  }
}
