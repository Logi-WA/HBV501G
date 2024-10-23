document.addEventListener("DOMContentLoaded", async () => {
  console.log("actions.js is loaded and DOM is ready");

  // Check if the user is logged in
  const isLoggedIn = await checkUserLoginStatus();

  // If logged in, attach event listeners to buttons
  if (isLoggedIn) {
    attachEventListeners();
  }

  // Reattach listeners when navigating back (e.g., from wishlist or cart pages)
  window.addEventListener("popstate", () => {
    console.log("Reattaching event listeners after navigation.");
    attachEventListeners();
  });
});

// Function to check if the user is logged in
async function checkUserLoginStatus() {
  try {
    const response = await fetch("/api/users/me", {
      method: "GET",
      credentials: "include",
    });

    if (response.ok) {
      console.log("User is logged in.");
      return true;
    } else {
      console.log("User is not logged in.");
      return false;
    }
  } catch (error) {
    console.error("Error checking user login status:", error);
    return false;
  }
}

// Attach event listeners to all buttons on the product cards
function attachEventListeners() {
  // Use event delegation
  const container = document.querySelector("#browse");
  if (!container) {
    console.error("Product container not found!");
    return;
  }

  // Add event listener to the container, listening for button clicks
  container.addEventListener("click", async (event) => {
    // Prevent scrolling
    event.preventDefault();

    const target = event.target;

    if (target.closest(".btn")?.id === "addToWish") {
      const productId = getProductIdFromCard(target);
      console.log("Attempting to add product to wishlist, ID:", productId);
      await addToWishlist(productId);
    } else if (target.closest(".btn")?.id === "addToCart") {
      const productId = getProductIdFromCard(target);
      console.log("Attempting to add product to cart, ID:", productId);
      await addToCart(productId);
    }
  });

  console.log("Event listeners attached to Wishlist and Cart buttons.");
}

// Helper function to extract product ID from a product card
function getProductIdFromCard(element) {
  const card = element.closest("[data-product-id]");
  const productId = card?.getAttribute("data-product-id");
  console.log("Extracted Product ID:", productId);
  return productId;
}

// Function to add product to the wishlist
async function addToWishlist(productId) {
  if (!productId) {
    console.error("No Product ID provided for wishlist");
    return;
  }
  console.log(`Attempting to add product ${productId} to wishlist`);
  try {
    const response = await fetch("/api/wishlist", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify({ productId }),
    });

    if (response.ok) {
      console.log("Product successfully added to wishlist.");
      alert("Product added to wishlist!");
    } else {
      const error = await response.text();
      console.error("Failed to add product to wishlist:", error);
      alert("Failed to add product to wishlist: " + error);
    }
  } catch (error) {
    console.error("Error adding to wishlist:", error);
  }
}

// Function to add product to the cart
async function addToCart(productId) {
  if (!productId) {
    console.error("No Product ID provided for cart");
    return;
  }
  console.log(`Attempting to add product ${productId} to cart`);
  try {
    const response = await fetch("/api/cart", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify({ productId }),
    });

    if (response.ok) {
      console.log("Product successfully added to cart.");
      alert("Product added to cart!");
    } else {
      const error = await response.text();
      console.error("Failed to add product to cart:", error);
      alert("Failed to add product to cart: " + error);
    }
  } catch (error) {
    console.error("Error adding to cart:", error);
  }
}
