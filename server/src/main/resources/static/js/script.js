window.addEventListener('load', login);

const rentalItemToken = 'RI_kCQzZkTC20';
const rentalTime = 1;
let checkoutUrl;

async function onClickPayment() {
  await requestPaymentUrl();

  if (checkoutUrl) {
    const params = {
      rentalItemToken: rentalItemToken,
      rentalTime: rentalTime
    };
    const queryString = new URLSearchParams(params).toString();
    fetch(`${checkoutUrl}?${queryString}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${sessionStorage.getItem('accessToken')}`
      }
    }).then(res => res.text())
    .then(html => {
      document.open();
      document.write(html);
      document.close();
    })
    .catch(error => console.error(error));

  }
}

async function login() {
  const loginData = {
    email: 'test@test.com',
    password: '1234'
  };
  await fetch('http://localhost:8080/v1/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginData),
  })
  .then(res => res.json())
  .then(data => {
    const accessToken = data.data.accessToken;
    sessionStorage.setItem('accessToken', accessToken);
  })
  .catch(error => console.error(error));
}

async function requestPaymentUrl() {
  const accessToken = sessionStorage.getItem('accessToken');
  await fetch('http://localhost:8080/v1/payments/checkout-url', {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${accessToken}`
    }
  }).then(res => res.json()).then(data => {
    checkoutUrl = data.data.checkoutUrl;
  });
}

