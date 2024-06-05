## Note

    th:hx-on-click="|document.getElementById('search-input').value='${record.name}';|"



## Old JS

```JavaScript
const autocomplete = (inputId, listId, dimensionCode) => {
    const input = document.getElementById(inputId)
    const list = document.getElementById(listId)
    if (!input || !list) return false
    list.innerHTML = ''

    let items = []
    let current = -1
    const bg_default = 'bg-white'
    const bg_select = 'bg-blue-500'
    const txt_default = 'text-black'
    const txt_select = 'text-white'

    // fetch all codes for the given dimension
    fetch(`/kenco-financials/dimensions/${dimensionCode}`)
        .then(response => response.json())
        .then(data => items = data.sort())

    input.addEventListener('keydown', (e) => {
        const key = e.key
        const children = list.children
        if (!children) return false
        list.classList.remove('hidden')
        if (key === 'ArrowDown' || key === 'ArrowUp' || key === 'Enter') removeAllActives(children)
        if (key === 'ArrowDown') {
            if (current < children.length - 1) current += 1
            updateList()
        } else if (key === 'ArrowUp') {
            if (current > 0) current -= 1
        } else if (key === 'Enter') {
            e.preventDefault()
            input.value = children[current].value
            list.classList.add('hidden')
            current = -1
        } else if (key === 'Tab') {
            list.classList.add('hidden')
        }
        setActive(children, current)
    })

    input.addEventListener('input', (e) => {
        updateList()
    })

    input.addEventListener('change', (e) => {
        let valid = false
        for (let i = 0; i < items.length; i++) {
            if (items[i].code.trim().toLowerCase() === input.value.trim().toLowerCase()) {
                valid = true
            }
        }
        if (!valid) input.value = ''
    })

    function updateList() {
        list.innerHTML = ''
        let filtered
        input.value.trim() === '' ? filtered = items : filtered = items.filter((item) => item.name.toLowerCase().includes(input.value.toLowerCase()))
        filtered.forEach((item) => {
            const opt = document.createElement('option')
            opt.innerText = `${item.name} - ${item.code}`
            opt.value = item.code
            opt.onclick = () => {
                input.value = item.code
                list.classList.add('hidden')
            }
            list.appendChild(opt)
        })
    }

    function removeAllActives(children) {
        if (!children) return false
        for (let i = 0; i < children.length; i++) {
            children[i].classList.remove(bg_select, txt_select)
            children[i].classList.add(bg_default, txt_default)
        }
    }

    function setActive(children, index) {
        if (!children || index === undefined || index < 0) return false
        if (index > children.length - 1) return false
        children[index].classList.add(bg_select, txt_select)
        children[index].classList.remove(bg_default, txt_default)
        children[index].scrollIntoView({block: 'nearest'})
    }
}
autocomplete('newBranch', 'branch-list', 'branch')
```