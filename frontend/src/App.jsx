import { useEffect, useState } from 'react';

function App() {

  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState([]);
  const [customer, setCustomer] = useState({
    customerName: '',
    email: ''
  });

  const [message, setMessage] = useState('');

  const loadProducts = async () => {
    const response = await fetch('/api/products');
    if (!response.ok) throw new Error('Unable to load products');
    const data = await response.json();
    setProducts(data);
  };

  useEffect(() => {
    loadProducts().catch(error => setMessage(error.message));
  }, []);

  const addToCart = (product) => {
    setCart(previous => {
      const existing = previous.find(
        item => item.id === product.id
      );

      if (existing) {
        return previous.map(item =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      }

      return [...previous, { ...product, quantity: 1 }];
    });
  };

  const placeOrder = async (product) => {

    if (!customer.customerName || !customer.email) {
      setMessage('Enter your name and email first');
      return;
    }

    try {
      const response = await fetch('/api/orders', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          customerName: customer.customerName,
          email: customer.email,
          productId: product.id,
          quantity: product.quantity
        })
      });

      const result = await response.json();

      if (!response.ok) {
        throw new Error(result.message || 'Order failed');
      }

      setMessage(`Order confirmed! Order ID: ${result.id}`);
      setCart(previous =>
        previous.filter(item => item.id !== product.id)
      );
      await loadProducts();

    } catch (error) {
      setMessage(error.message);
    }
  };

  return (
    <div className="container">

      <header>
        <h1>Online Shopping Store</h1>
        <p>Welcome! Shop your favourite products.</p>
      </header>

      <section className="customer">
        <input
          placeholder="Customer Name"
          value={customer.customerName}
          onChange={e => setCustomer({
            ...customer,
            customerName: e.target.value
          })}
        />

        <input
          placeholder="Email"
          type="email"
          value={customer.email}
          onChange={e => setCustomer({
            ...customer,
            email: e.target.value
          })}
        />
      </section>

      <h2>Available Products</h2>

      <div className="products">
        {products.map(product => (
          <div className="card" key={product.id}>

            <h3>{product.name}</h3>
            <p>{product.description}</p>
            <h3>₹{product.price}</h3>
            <p>Stock: {product.stock}</p>

            <button
              disabled={product.stock <= 0}
              onClick={() => addToCart(product)}
            >
              Add to Cart
            </button>

          </div>
        ))}
      </div>

      <h2>Shopping Cart</h2>

      {cart.map(item => (
        <div className="cart-item" key={item.id}>
          {item.name} — Quantity: {item.quantity}
          <button onClick={() => placeOrder(item)}>
            Place Order
          </button>
        </div>
      ))}

      {message && <h3>{message}</h3>}

    </div>
  );
}

export default App;