document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/topics')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const topicList = document.getElementById('topicList');
            data.forEach(topic => {
                const listItem = document.createElement('li');
                const container = document.createElement('div');
                container.className = 'topic-container';

                // Başlık ve entryCount
                const titleElement = document.createElement('h2');
                titleElement.textContent = topic.title;

                const entryCountElement = document.createElement('span');
                entryCountElement.className = 'entry-count';
                entryCountElement.textContent = `Entry Sayısı: ${topic.entryCount || 0}`;

                // Yorum simgesi (buton)
                const commentIcon = document.createElement('button');
                commentIcon.className = 'comment-icon';
                commentIcon.textContent = '💬';  // Yorum simgesi
                commentIcon.addEventListener('click', () => {
                    alert(`Başlık: ${topic.title} - Entry Sayısı: ${topic.entryCount}`);
                });

                container.appendChild(titleElement);
                container.appendChild(entryCountElement);
                container.appendChild(commentIcon);
                listItem.appendChild(container);
                topicList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error));
});