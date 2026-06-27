import { useState } from "react";
import axios from "axios";
import "./App.css";

function App() {

  const [latestShortUrl, setLatestShortUrl] = useState("");
  const [url, setUrl] = useState("");
  const [links, setLinks] = useState([]);
  const [error, setError] = useState("");

  const shortenUrl = async () => {

    try {

      const res = await axios.post(
        "http://localhost:8080/api/shorten",
        {
          originalUrl: url,
          expiryDays: 30 // keeping backend happy
        }
      );

      const shortUrl = `http://localhost:8080/s/${res.data.shortKey}`;

      setLatestShortUrl(shortUrl);

      setLinks([
        {
          ...res.data,
          shortUrl
        },
        ...links
      ]);

      setUrl("");
      setError("");

    } catch (err) {

      console.log(err);
      setError("Failed to fetch");

    }

  };

  return (

    <div className="page">

      <nav>

        <div className="logo">
          🔗 Linkly
        </div>

        <div className="tag">
          URL Shortener
        </div>

      </nav>

      <div className="main">

        <h1>
          Welcome to Linkly 👋
        </h1>

        <p>
          Paste any long URL and get a short link you can share anywhere.
        </p>

        <div className="box">

          <label>
            NEW LINK
          </label>

          <div className="inputRow">

            <input
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              placeholder="Paste your long URL"
            />

            <button onClick={shortenUrl}>
              ➤ Shorten
            </button>

          </div>

          {error &&
            <p className="error">
              {error}
            </p>
          }

          {latestShortUrl && (

            <div className="resultBox">

              <input
                type="text"
                value={latestShortUrl}
                readOnly
              />

              <button
                onClick={() => {
                  navigator.clipboard.writeText(latestShortUrl);
                  alert("Copied!");
                }}
              >
                Copy
              </button>

            </div>

          )}

        </div>

        <div className="links">

          <h3>
            ALL LINKS
          </h3>

          {

            links.length === 0 ?

              <div className="empty">

                🔗

                <p>
                  No links yet — shorten your first URL above.
                </p>

              </div>

              :

              <table>

                <thead>

                  <tr>

                    <th>Original URL</th>

                    <th>Short URL</th>

                    <th>Created At</th>

                    <th>Expires At</th>

                  </tr>

                </thead>

                <tbody>

                  {

                    links.map((item, index) => (

                      <tr key={index}>

                        <td>{item.originalUrl}</td>

                        <td>

                          <a
                            href={item.shortUrl}
                            target="_blank"
                            rel="noopener noreferrer"
                          >
                            {item.shortUrl}
                          </a>

                        </td>

                        <td>
                          {new Date(item.createdAt).toLocaleString()}
                        </td>

                        <td>
                          {new Date(item.expiresAt).toLocaleString()}
                        </td>

                      </tr>

                    ))

                  }

                </tbody>

              </table>

          }

        </div>

      </div>

    </div>

  );

}

export default App;