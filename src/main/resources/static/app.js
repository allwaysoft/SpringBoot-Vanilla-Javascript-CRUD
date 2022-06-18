const tableList = document.querySelector('.table-list') //contenedor de las filas con las persons
const addPersonForm = document.querySelector('.add-person-form') //formulario agregar person
const nameValue = document.getElementById('name-value') //id del input donde ponemos el name
const ageValue = document.getElementById('age-value') //id del input donde ponemos la age

const btnSubmit = document.querySelector('.btn-submit') //boton submit del form


let output = '';

// let url = 'post.json'

let url = 'http://localhost:8080/api/person'



//GET - READ THE POST
//Method : GET
const getPerson = (url) => {
    fetch(url)
            .then(res => res.json())
            .then(data => renderPerson(data))
}
getPerson(url)




const renderPerson = data => {
    data.forEach(person => {
        // console.log(person)
        const {id, name, age} = person
        output += `
        <tr>
            <th scope="row">${id}</th>
            <td class='rowName'>${name}</td>
            <td class='rowAge'>${age}</td>
            <td id=${id}> <a href="#" class="btn btn-primary" id="edit">Edit</a> <a href="#" class="btn btn-danger" id="delete">Delete</a></td>
        </tr>
        `
    });
    tableList.innerHTML = output;
}



//Create - insert THE POST
//Method : POST
addPersonForm.addEventListener('submit', e => {
    e.preventDefault() // para evitar que se recargue la pagina y podamos trabajar con este form
    if (nameValue.value == '' || ageValue.value == '') {
        alert('You must complete the requested data')
    } else if ((/\d/g).test(nameValue.value) || (/\D/g).test(ageValue.value)) {
        alert('The name must be composed of letters and the age with numbers')
    } else {
        fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                name: nameValue.value,
                age: ageValue.value
            })
        })
                .then(res => res.json())
                .then(data => {
                    const dataArr = [];
                    dataArr.push(data);
                    renderPerson(dataArr)
                })
    }
    //una vez que agrego la person, reseteo el input para que quede vacio
    nameValue.value = '';
    ageValue.value = '';
})


//Delete and update
//Method : DELETE y PUT
tableList.addEventListener('click', (e) => {
    e.preventDefault()
    // console.log(e.target.id)
    const btnDelete = e.target.id == 'delete'
    const btnEdit = e.target.id == 'edit'
    const idBnt = e.target.parentElement.id //parentElement me trae el elemento padre, el td y al poner id, me trae el id de ese td

    //Delete - eliminar person
    if (btnDelete) {
        fetch(`${url}/${idBnt}`, {
            method: 'DELETE'
        })
        // .then(res=> res.json())
        // .then(() => location.reload())
        location.reload()
    }

    //update - editar person
    if (btnEdit) {
        const parent = e.target.parentElement.parentElement //td => elemento padre
        let nameContent = parent.querySelector('.rowName').textContent //tengo el valor del input que tiene el name
        let ageContent = parent.querySelector('.rowAge').textContent //tengo la age

        nameValue.value = nameContent
        ageValue.value = ageContent

        btnSubmit.innerText = 'Save Person'

    }

    btnSubmit.addEventListener('click', e => {
        e.preventDefault();
        fetch(`${url}/${idBnt}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                name: nameValue.value,
                age: ageValue.value
            })
        })
                .then(res => res.json())
                .then(() => location.reload())
        // window.location.reload()
        btnSubmit.innerText = 'Add Person'

    })


})



