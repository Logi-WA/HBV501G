document.addEventListener('DOMContentLoaded', async () => {
  // Check if the user is logged in
  const isLoggedIn = await checkUserLoginStatus();

  // If logged in, attach event listeners to buttons
  if (isLoggedIn) {
    attachEventListeners();
  }

  // Reattach listeners when navigating back (e.g., from wishlist or cart pages)
  window.addEventListener('popstate', () => {
    attachEventListeners();
  });
});

// Function to check if the user is logged in
async function checkUserLoginStatus() {
  try {
    const response = await fetch('/api/users/me', {
      method: 'GET',
      credentials: 'include',
    });

    if (response.ok) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    return false;
  }
}

// Attach event listeners to all buttons on the product cards
function attachEventListeners() {
  // Use event delegation
  const container = document.getElementById('browse');
  if (!container) {
    return;
  }

  // Add event listener to the container, listening for button clicks
  container.addEventListener('click', async (event) => {
    // Prevent scrolling
    event.preventDefault();

    const target = event.target;

    if (target.closest('.btn')?.classList.contains('addToWish')) {
      const productId = getProductIdFromCard(target);
      await addToWishlist(productId);
    } else if (target.closest('.btn')?.classList.contains('addToCart')) {
      const productId = getProductIdFromCard(target);
      await addToCart(productId);
    }
  });
}

// Helper function to extract product ID from a product card
function getProductIdFromCard(element) {
  const card = element.closest('[data-product-id]');
  const productId = card?.getAttribute('data-product-id');
  return productId;
}

// Function to add product to the wishlist
async function addToWishlist(productId) {
  if (!productId) {
    return;
  }
  try {
    const response = await fetch('/api/wishlist', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId: parseInt(productId, 10) }),
      credentials: 'include',
    });

    if (response.ok) {
      alert('Product added to wishlist!');
    } else {
      const error = await response.text();
      alert('Failed to add product to wishlist: ' + error);
    }
  } catch (error) {
    console.error('Error adding to wishlist:', error);
  }
}

// Function to add product to the cart
async function addToCart(productId) {
  if (!productId) {
    console.error('No Product ID provided for cart');
    return;
  }
  try {
    const response = await fetch('/api/cart', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ productId: parseInt(productId, 10) }),
      credentials: 'include',
    });

    if (response.ok) {
      alert('Product added to cart!');
    } else {
      const error = await response.text();
      alert('Failed to add product to cart: ' + error);
    }
  } catch (error) {
    console.error('Error adding to cart:', error);
  }
}

/**
 * Fetches all users from the server and displays them in the `usersList` section.
 * This function is triggered by the "Get All Users" button on the frontend.
 */
async function fetchAllUsers() {
  try {
    // Send a GET request to fetch all users
    const response = await fetch('/api/users', {
      method: 'GET',
      credentials: 'include', // Ensures session information is included
    });

    if (response.ok) {
      // Parse JSON response if the request is successful
      const users = await response.json();
      displayUsers(users); // Display users in the HTML list
    } else {
      alert("Failed to retrieve users."); // Notify user of an unsuccessful request
    }
  } catch (error) {
    console.error("Error fetching users:", error); // Log any network or server errors
  }
}

/**
 * Displays the list of users in an HTML unordered list (`<ul>`).
 * Each user is shown as a list item (`<li>`), displaying their name and email.
 *
 * @param {Array} users - The list of user objects to display, each with `name` and `email`.
 */
function displayUsers(users) {
  const usersList = document.getElementById("usersList");
  usersList.innerHTML = ''; // Clear any existing list items

  users.forEach(user => {
    // Create a new list item for each user
    const listItem = document.createElement("li");
    listItem.textContent = `${user.name} (${user.email})`; // Format: Name (Email)
    usersList.appendChild(listItem); // Append list item to the usersList
  });
}
