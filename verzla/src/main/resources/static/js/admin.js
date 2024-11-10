document.addEventListener('DOMContentLoaded', () => {
  // Attach event listener for edit buttons
  document.querySelectorAll('.edit-product-button').forEach(button => {
    button.addEventListener('click', (event) => {
      const productRow = button.closest('[data-product-id]');
      const productId = productRow?.getAttribute('data-product-id');

      if (!productId) {
        console.error("Product ID is missing.");
        alert("Product ID is missing.");
        return;
      }

      // Populate modal with product details
      document.getElementById('editProductId').value = productId;
      document.getElementById('newValue').value = productRow.querySelector('td:nth-child(2)').innerText;
      document.getElementById('fieldSelect').value = 'name';

      console.log("Product ID:", productId);
      const modal = new bootstrap.Modal(document.getElementById('editModal'));
      modal.show();
    });
  });

  // Save Changes button event listener
  document.getElementById('saveChanges').addEventListener('click', async () => {
    const productId = document.getElementById('editProductId').value;
    const field = document.getElementById('fieldSelect').value;
    const newValue = document.getElementById('newValue').value;

    if (!productId || !newValue) {
      console.error("Product ID or new value is missing.");
      alert("Product ID or new value is missing.");
      return;
    }

    console.log("Updating product - ID:", productId, "Field:", field, "New Value:", newValue);

    await updateProductField(productId, field, newValue);
    bootstrap.Modal.getInstance(document.getElementById('editModal')).hide();
  });
});

// Function to update product name or description
async function updateProductField(productId, field, newValue) {
  const endpoint = field === 'name'
    ? `/api/products/${productId}/name`
    : `/api/products/${productId}/description`;

  try {
    const response = await fetch(endpoint, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ [field]: newValue })
    });

    if (response.ok) {
      alert(`Product ${field} updated successfully!`);
      location.reload(); // Reload page to reflect changes
    } else {
      const error = await response.text();
      alert(`Failed to update product ${field}: ${error}`);
    }
  } catch (error) {
    console.error(`Error updating product ${field}:`, error);
  }
}