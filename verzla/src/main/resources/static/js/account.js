/**
 * Updates the account details (name and email) by sending a PATCH request to the server.
 * It validates that both name and email fields are filled in before sending the request.
 */
async function updateAccountDetails() {
  const name = document.getElementById('accountInputName').value.trim();
  const email = document.getElementById('accountInputEmail').value.trim();

  console.log('Name: ', name);
  console.log('Email: ', email);

  // Check if name and email are filled
  if (!name || !email) {
    alert('Please fill in all fields.');
    return;
  }

  // Create an object with the user details to be sent to the server
  const userDetails = {
    name: name,
    email: email,
  };

  try {
    // Send a PATCH request to update user details
    const response = await fetch('/api/users/me', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userDetails),
      credentials: 'include',
    });

    // Handle the response from the server
    if (response.ok) {
      alert('Account details updated successfully.');
      // Optionally, update the navbar with the new name and email
      updateNavbarAfterAccountUpdate();
    } else {
      const errorText = await response.text();
      alert('Failed to update account details: ' + errorText);
    }
  } catch (error) {
    alert('Error: ' + error.message);
  }
}

/**
 * Fetches the updated user details and refreshes the navbar to reflect the new account information.
 */
async function updateNavbarAfterAccountUpdate() {
  try {
    const response = await fetch('/api/users/me', {
      method: 'GET',
      credentials: 'include',
    });

    if (response.ok) {
      const userData = await response.json();
      // Remove existing user dropdown if it exists
      const userDropdown = document.querySelector('.dropdown-user');
      if (userDropdown) {
        userDropdown.remove();
      }
      // Update navbar with the new user data
      updateNavbarLoggedIn(userData);
    }
  } catch (error) {
    console.error('Error updating navbar:', error);
  }
}

/**
 * Updates the user's password by sending a PATCH request to the server.
 * Validates that all password fields are filled and that the new passwords match.
 */
async function updatePassword() {
  const currentPassword = document.getElementById('currentPassword').value;
  const newPassword = document.getElementById('newPassword').value;
  const confirmPassword = document.getElementById('confirmPassword').value;

  // Check if all password fields are filled
  if (!currentPassword || !newPassword || !confirmPassword) {
    alert('Please fill in all password fields.');
    return;
  }

  // Validate that new passwords match
  if (newPassword !== confirmPassword) {
    alert('New passwords do not match.');
    return;
  }

  try {
    // Send a PATCH request to update the password
    const response = await fetch('/api/users/me/password', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        currentPassword: currentPassword,
        newPassword: newPassword,
      }),
      credentials: 'include',
    });

    // Handle the server response
    if (response.ok) {
      alert('Password updated successfully.');
      // Clear the password fields after successful update
      document.getElementById('currentPassword').value = '';
      document.getElementById('newPassword').value = '';
      document.getElementById('confirmPassword').value = '';
    } else {
      const errorText = await response.text();
      alert('Failed to update password: ' + errorText);
    }
  } catch (error) {
    alert('Error: ' + error.message);
  }
}
