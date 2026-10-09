const cart = {};          // { productId: { product, quantity } }
let products = [];

const money = n => "₹" + Number(n).toLocaleString("en-IN");

async function loadProducts() {
  const res = await fetch("/api/products");
  products = await res.json();
  const grid = document.getElementById("products");
  grid.innerHTML = "";
  products.forEach(p => {
    const card = document.createElement("div");
    card.className = "card";
    card.innerHTML = `
      <div class="emoji">${p.emoji}</div>
      <h3>${p.name}</h3>
      <p>${p.description}</p>
      <div class="price">${money(p.price)}</div>
      <button data-id="${p.id}">Add to cart</button>`;
    card.querySelector("button").addEventListener("click", () => addToCart(p.id));
    grid.appendChild(card);
  });
}

function addToCart(id) {
  const product = products.find(p => p.id === id);
  if (!cart[id]) cart[id] = { product, quantity: 0 };
  cart[id].quantity++;
  renderCart();
}

function changeQty(id, delta) {
  cart[id].quantity += delta;
  if (cart[id].quantity <= 0) delete cart[id];
  renderCart();
}

function renderCart() {
  const list = document.getElementById("cart-items");
  list.innerHTML = "";
  let total = 0, count = 0;
  Object.values(cart).forEach(({ product, quantity }) => {
    total += product.price * quantity;
    count += quantity;
    const li = document.createElement("li");
    li.innerHTML = `<span>${product.name} × ${quantity}</span>
      <span>${money(product.price * quantity)}
        <button>-</button><button>+</button></span>`;
    const [minus, plus] = li.querySelectorAll("button");
    minus.addEventListener("click", () => changeQty(product.id, -1));
    plus.addEventListener("click", () => changeQty(product.id, 1));
    list.appendChild(li);
  });
  document.getElementById("cart-total").textContent = money(total);
  document.getElementById("cart-count").textContent = count;
}

async function checkout() {
  const msg = document.getElementById("message");
  const items = Object.values(cart).map(c => ({ productId: c.product.id, quantity: c.quantity }));
  const res = await fetch("/api/orders", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ items })
  });
  if (res.ok) {
    const order = await res.json();
    msg.className = "ok";
    msg.textContent = `Order #${order.id} placed! Total ${money(order.total)}`;
    Object.keys(cart).forEach(k => delete cart[k]);
    renderCart();
  } else {
    msg.className = "err";
    msg.textContent = "Could not place order. Is your cart empty?";
  }
}

document.getElementById("cart-toggle").addEventListener("click", () =>
  document.getElementById("cart").classList.toggle("hidden"));
document.getElementById("checkout").addEventListener("click", checkout);

loadProducts();
