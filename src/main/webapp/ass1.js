const Ass1 = {};
Ass1.games = null;

Ass1.$loading = document.getElementById('loading');
Ass1.$unexpectedError = document.getElementById('unexpectedError');
Ass1.$noEntries = document.getElementById('noEntries');

Ass1.$list = document.getElementById('list');
Ass1.$listBody = document.getElementById('listBody');
Ass1.$addForm = document.getElementById('addForm');

Ass1.$tableEntryTpl = document.getElementById('template_entry');
Ass1.tableEntryTpl = Handlebars.compile(Ass1.$tableEntryTpl.innerHTML);

Ass1.$formTpl = document.getElementById('template_form');
Ass1.formTpl = Handlebars.compile(Ass1.$formTpl.innerHTML);

Ass1.$refreshBtn = document.getElementById('refreshBtn');

Ass1.$content = document.getElementById('content');
Ass1.$edit = document.getElementById('edit');
Ass1.$editForm = document.getElementById('editForm');

Ass1.$searchByName = document.getElementById('searchByName');
Ass1.$getExternalList = document.getElementById('getExternalList');

Ass1.init = async function init() {
	Ass1.$refreshBtn.addEventListener('click', Ass1.refresh);
	Ass1.refresh();
	Ass1.$addForm.innerHTML = Ass1.formTpl({});
	Ass1.$addForm = Ass1.$addForm.getElementsByTagName('form')[0];
	Ass1.$addForm.addEventListener('submit', Ass1.addNewEntry);
	
	Ass1.$searchByName.addEventListener('submit', Ass1.searchByName);
	Ass1.$getExternalList.addEventListener('click', Ass1.getExternalList);
};

Ass1.getExternalList = async function getExternalList(ev) {
	ev.preventDefault();
	
	window.location.href = '/ass1/games/external';
};

Ass1.searchByName = async function searchByName(ev) {
	ev.preventDefault();
	
	window.location.href = '/ass1/games/find/name/' + Ass1.$searchByName.querySelector('input').value;
};

Ass1.refresh = async function refresh() {
	try {
		Ass1.$loading.classList.remove('hidden');
		Ass1.$list.classList.add('hidden');
		Ass1.$noEntries.classList.add('hidden');
		Ass1.$listBody.innerHTML = '';
		
		const response = await request('/ass1/games', {
			method: 'GET'
		});
		
		Ass1.games = response.games;
		Ass1.$listBody.innerHTML = '';
		
		if (Ass1.games.length) {
			Ass1.games.forEach(g => {
				const tr = document.createElement('tr');
				tr.innerHTML = Ass1.tableEntryTpl(g);
				tr.setAttribute('data-id', g.id);
				Ass1.$listBody.append(tr);
			});
			Ass1.$list.classList.remove('hidden');
			
			[...Ass1.$list.querySelectorAll('.editBtn')].forEach(e => e.addEventListener('click', Ass1.editEntry));
			[...Ass1.$list.querySelectorAll('.deleteBtn')].forEach(e => e.addEventListener('click', Ass1.deleteEntry));
		} else {
			Ass1.$noEntries.classList.remove('hidden');
		}
	
		Ass1.$loading.classList.add('hidden');
	} catch (e) {
		console.error(e);
		Ass1.$unexpectedError.classList.remove('hidden');
	}
};

Ass1.editEntry = async function editEntry(ev) {
	const $tr = ev.target.closest('tr');
	const id = $tr.getAttribute('data-id');

	try {
		const response = await request('/ass1/games/' + id, {
			method: 'GET',
		});
		
		Ass1.$editForm.innerHTML = Ass1.formTpl(response.game);
		Ass1.$content.classList.add('hidden');
		Ass1.$edit.classList.remove('hidden');
		
		const $form = Ass1.$editForm.querySelector('form');
		$form.addEventListener('submit', async (ev) => {
			ev.preventDefault();
			
			try {
				await request('/ass1/games', {
				    headers: {
				        'Accept': 'application/json',
				        'Content-Type': 'application/json'
				    },
					method: 'POST',
					body: JSON.stringify({
						game: {
							...getFormContent($form),
							id,
						}
					}),
				});
				
				Ass1.$content.classList.remove('hidden');
				Ass1.$edit.classList.add('hidden');
				
				Ass1.refresh();
			} catch (e) {
				console.error(e);
				Ass1.$unexpectedError.classList.remove('hidden');
				
				Ass1.$content.classList.remove('hidden');
				Ass1.$edit.classList.add('hidden');
			}
		});
	} catch (e) {
		console.error(e);
		Ass1.$unexpectedError.classList.remove('hidden');
	}
};

Ass1.deleteEntry = async function deleteEntry(ev) {
	const $tr = ev.target.closest('tr');
	const id = $tr.getAttribute('data-id');
	
	try {
		await request('/ass1/games/' + id, {
			method: 'DELETE',
		});
		
		Ass1.refresh();
	} catch (e) {
		console.error(e);
		Ass1.$unexpectedError.classList.remove('hidden');
	}
};

Ass1.addNewEntry = async function addNewEntry(ev) {
	ev.preventDefault();
	
	try {
		const response = await request('/ass1/games', {
		    headers: {
		        'Accept': 'application/json',
		        'Content-Type': 'application/json'
		    },
			method: 'PUT',
			body: JSON.stringify({game: getFormContent(Ass1.$addForm)}),
		});
		
		Ass1.$addForm.reset();
		
		Ass1.refresh();
	} catch (e) {
		console.error(e);
		Ass1.$unexpectedError.classList.remove('hidden');
	}
};

const getFormContent = ($form) => {
	return [...$form.querySelectorAll('[name]')]
		.reduce((obj, e) => ({...obj, [e.name]: e.value}), {});
};

//WeatherFetcher.search = async function search(event) {
//	event.preventDefault();
//
//	WeatherFetcher.$unknownStation.classList.add('hidden');
//	WeatherFetcher.$unexpectedError.classList.add('hidden');
//	WeatherFetcher.$weatherReport.innerHTML = '';
//	
//	const value = WeatherFetcher.$searchInput.value;
//	if (!value || !value.trim().length) {
//		return;
//	}
//	
//	const headers = new Headers();
//	headers.append('Content-Type', 'application/json');
//	
//	try {
//		const response = await request('api/stations', {
//			method: 'POST',
//			body: JSON.stringify({
//				name: value.trim()
//			}),
//			headers: headers
//		});
//		
//		WeatherFetcher.$weatherReport.innerHTML =
//			WeatherFetcher.weatherReportTemplate(response.station);
//	} catch (e) {
//		if (e.response && e.response.status === 404) {
//			WeatherFetcher.$unknownStation.querySelector('.stationName').innerText = value;
//			WeatherFetcher.$unknownStation.classList.remove('hidden');
//		} else {
//			WeatherFetcher.$unexpectedError.classList.remove('hidden');
//		}
//	};
//};

window.addEventListener("load", Ass1.init);

// wrap request to react to error
const request = async (...args) => {
	Ass1.$unexpectedError.classList.add('hidden');
	
	const response = await fetch(...args);
	const json = await response.json();
	const {success, ...data} = json;
	
	if (!success) {
		throw {
			response
		};
	}
	
	return data;
};
