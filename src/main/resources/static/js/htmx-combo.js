/**
 * Bind events to a particular combo box (be sure to change input and searchItems, etc. as you see fit)
 * @author cbmeeks
 * @returns {boolean}
 */
const bindCombo = () => {
    const input = document.getElementById('search-input')
    const searchItems = document.getElementById('search-items')
    if (!input || !searchItems) return false;

    let index = -1
    const list = searchItems.children
    const bg_selected = 'bg-blue-200'

    // Hide items if user clicks somewhere else
    document.body.addEventListener('click', (e) => {
        // if (e.target !== input) searchItems.classList.add('hidden')
    })

    input.addEventListener('keydown', (e) => {
        if (e.key === 'ArrowDown') handleKeyDown()
        if (e.key === 'ArrowUp') handleKeyUp()
        if (e.key === 'Enter') handleEnterKey()
        if (index >= 0 && index < list.length - 1) searchItems.children[index].scrollIntoView({behavior: "auto"})
    })

    const handleKeyDown = () => {
        if (!list || list.length === 0) return false
        if (index >= 0) list[index].classList.remove(bg_selected)
        if (index < list.length - 1) index++
        list[index].classList.add(bg_selected)
    }

    const handleKeyUp = () => {
        if (!list || list.length === 0) return false
        if (index <= list.length) list[index].classList.remove(bg_selected)
        if (index > 0) index--
        list[index].classList.add(bg_selected)
    }

    const handleEnterKey = () => {
        if (!list || list.length === 0) return false
        if (list[index]) list[index].click()
    }
}

/**
 * Custom event listener.  When HTMX swaps out or loads an input, you can fire the event from the backend and
 * re-bind the listeners for the combos
 */
document.body.addEventListener('bindCombos', (e) => {
    bindCombo();
})

/**
 * Load the initial events
 */
bindCombo();

