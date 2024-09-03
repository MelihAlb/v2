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
            topicList.innerHTML = '';

            data.forEach(topic => {
                const listItem = document.createElement('li');
                const container = document.createElement('div');
                container.className = 'container';

                const titleElement = document.createElement('h1');
                const link = document.createElement('a');
                link.href = `topic.html?id=${topic.id}`;
                link.textContent = topic.title;

                const entryCountElement = document.createElement('span');
                const entryCount = topic.entryCount || 0;

                const commentIcon = document.createElement('button');
                commentIcon.className = 'comment-icon';
                commentIcon.innerHTML = `💬 <span class="entry-count">${entryCount}</span>`;

                titleElement.appendChild(link);
                container.appendChild(titleElement);
                container.appendChild(commentIcon);
                listItem.appendChild(container);
                topicList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error)
);


});
