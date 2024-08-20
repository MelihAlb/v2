document.addEventListener('DOMContentLoaded', function() {
    async function fetchTopics() {
        try {
            const response = await fetch('http://localhost:8080/topics');
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const topics = await response.json();
            displayTopics(topics);
        } catch (error) {
            console.error('Error fetching topics:', error);
        }
    }

    async function fetchEntriesForTopic(topicId) {
        try {
            const response = await fetch(`http://localhost:8080/entries/topic/${topicId}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const entries = await response.json();
            return entries;
        } catch (error) {
            console.error(`Error fetching entries for topic ${topicId}:`, error);
            return [];
        }
    }

    function calculatePadding(titleLength) {

        const minPadding = 20;
        const maxPadding = 80;
        const baseLength = 20;
        return Math.min(Math.max(minPadding, (titleLength / baseLength) * 20), maxPadding) + 'px';
    }

    async function displayTopics(topics) {
        const topicsContainer = document.getElementById('topics-container');
        topicsContainer.innerHTML = '';

        for (const topic of topics) {
            const entries = await fetchEntriesForTopic(topic.id);
            const entryCount = entries.length;

            const topicElement = document.createElement('div');
            topicElement.className = 'topic-card';


            const titleLength = topic.title.length;
            const padding = calculatePadding(titleLength);

            topicElement.innerHTML = `
                <img src="https://via.placeholder.com/150" alt="${topic.title}" />
                <h2>${topic.title}</h2>
                <div class="entry-info" style="padding: ${padding};">
                    <i class="fas fa-comment"></i>
                    <span>${entryCount}</span>
                </div>
            `;

            topicsContainer.appendChild(topicElement);
        }
    }

    fetchTopics();
});
