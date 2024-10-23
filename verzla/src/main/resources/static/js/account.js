async function updateAccountDetails() {
  const name = document.getElementById('accountInputName').value.trim();
  const email = document.getElementById('accountInputEmail').value.trim();

  console.log('Name: ', name);
  console.log('Email: ', email);

  if (!name || !email) {
    alert('Please fill in all fields.');
    return;
  }

  const userDetails = {
    name: name,
    email: email,
  };

  try {
    const response = await fetch('/api/users/me', {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(userDetails),
      credentials: 'include',
    });

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

async function updateNavbarAfterAccountUpdate() {
  try {
    const response = await fetch('/api/users/me', {
      method: 'GET',
      credentials: 'include',
    });
    if (response.ok) {
      const userData = await response.json();
      // Remove existing user dropdown
      const userDropdown = document.querySelector('.dropdown-user');
      if (userDropdown) {
        userDropdown.remove();
      }
      // Update navbar with new user data
      updateNavbarLoggedIn(userData);
    }
  } catch (error) {
    console.error('Error updating navbar:', error);
  }
}

async function updatePassword() {
  const currentPassword = document.getElementById('currentPassword').value;
  const newPassword = document.getElementById('newPassword').value;
  const confirmPassword = document.getElementById('confirmPassword').value;

  if (!currentPassword || !newPassword || !confirmPassword) {
    alert('Please fill in all password fields.');
    return;
  }

  if (newPassword !== confirmPassword) {
    alert('New passwords do not match.');
    return;
  }

  try {
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

    if (response.ok) {
      alert('Password updated successfully.');
      // Clear the password fields
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
