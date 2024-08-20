// Function to fetch entries from the backend
async function fetchEntries() {
    try {
        const response = await fetch('http://localhost:8080/entries'); // Backend API URL
        const entries = await response.json();
        const entriesList = document.getElementById('entries-list');

        entriesList.innerHTML = ''; // Clear existing entries

        entries.forEach(entry => {
            const li = document.createElement('li');

            // Create elements for content and like count
            const contentSpan = document.createElement('span');
            contentSpan.className = 'content';
            contentSpan.textContent = entry.content;

            const likeCountSpan = document.createElement('span');
            likeCountSpan.className = 'likeCount';
            likeCountSpan.textContent = `Likes: ${entry.likeCount}`;

            // Append elements to list item
            li.appendChild(contentSpan);
            li.appendChild(likeCountSpan);

            // Append list item to list
            entriesList.appendChild(li);
        });
    } catch (error) {
        console.error('Error fetching entries:', error);
    }
}

// Fetch entries on page load
document.addEventListener('DOMContentLoaded', fetchEntries);
