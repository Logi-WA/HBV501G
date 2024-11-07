window.addEventListener('DOMContentLoaded', (event) => {
  const usersTable = document.getElementById('datatablesUsers');
  if (usersTable) {
    new simpleDatatables.DataTable(usersTable);
  }

  const productsTable = document.getElementById('datatablesProducts');
  if (productsTable) {
    new simpleDatatables.DataTable(productsTable);
  }
});
