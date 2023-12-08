<template>
    <div>
        <div class="container-fluid mt-2">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <!-- Card header -->
                        <div class="card-header border-0">
                            <h3 class="mb-0">Psychologist List</h3>
                        </div>
                        <!-- Light table -->
                        <div class="table-responsive">
                            <table class="table align-items-center table-flush">
                                <thead class="thead-light">
                                    <tr>
                                        <th scope="col" class="sort" data-sort="name">Psychologist</th>
                                        <th scope="col" class="sort" data-sort="budget">Practice Number</th>
                                        <th scope="col">Speciality</th>
                                        <th scope="col">Status</th>
                                        <th scope="col" class="sort" data-sort="completion">Date Registered</th>
                                        <!-- <th scope="col"></th> -->
                                    </tr>
                                </thead>
                                <tbody class="list" id="patientlist">
                                    <tr 
                                    v-for="item in psychologists" 
                                    :key="item.psychID" 
                                    @click="newRedirect('editaccount', item.psychID)"
                                    class="cursorClick">
                                        <th scope="row">
                                        <div class="media align-items-center">
                                            <div class="avatar rounded-circle mr-3">
                                                <img :src="item.profilePicture" class="avatar rounded-circle">
                                            </div>
                                            <div class="media-body">
                                                <span class="name mb-0 text-sm" style="color: #32325d">{{ item.name }}</span>
                                            </div>
                                        </div>
                                        </th>
                                        <td class="budget">
                                            {{ item.regNumber }}
                                        </td>
                                        <td class="budget">
                                            {{ item.speciality }}
                                        </td>
                                        <td>
                                            <span class="badge badge-dot mr-4">
                                                <i :class="item.status == 'Active' ? 'bg-success' : 'bg-warning'"></i>
                                                <span class="status">{{ item.status }}</span>
                                            </span>
                                        </td>
                                        <td>
                                            {{ item.dateRegistered}}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- Card footer -->
                        <div class="card-footer py-4">
                            <nav aria-label="...">
                                <ul class="pagination justify-content-end mb-0">
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#" tabindex="-1">
                                            <i class="fas fa-angle-left"></i>
                                            <span class="sr-only">Previous</span>
                                        </a>
                                    </li>
                                    <li class="page-item active">
                                        <a class="page-link" href="#">1</a>
                                    </li>
                                    <!--<li class="page-item">
                                        <a class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
                                    </li>
                                    <li class="page-item"><a class="page-link" href="#">3</a></li>-->
                                    <li class="page-item disabled">
                                        <a class="page-link" href="#">
                                            <i class="fas fa-angle-right"></i>
                                            <span class="sr-only">Next</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import moment from 'moment'

export default {

    data() {
		return {
            psychologists: 
            [
                // {
                //     psychID: 0,
                //     profilePicture: '',
                //     name: 'Sue Lonely',
                //     regNumber: '12daf',
                //     speciality: 'Child',
                //     dateRegistered: '12 September 2021',
                //     status: 'Active'
                // },
            ],
            
        }
    },
    
    async mounted() {
        await this.getAllPsychologists()
    },

    methods: {
        async getAllPsychologists()
        {
            const resp = await this.$store.dispatch('users/getAllPsychologists')
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

            resp.forEach(element => {
                //if(element.status == "Active")
                {
                    var temp =
                    {
                        psychID: element.userID,
                        profilePicture: images(`./${element.profilePicture}`),
                        name: element.name,
                        regNumber: element.regNumber,
                        speciality: element.speciality,
                        dateRegistered: moment(element.dateRegistered).format('DD MMMM YYYY'),
                        status: element.status
                    }
                    this.psychologists.push(temp)
                }
            });
        },

        newRedirect(page, paramValue) {
            if(paramValue != undefined)
            {
                this.$router.push({ path: page, query: { id: paramValue } })
            }
        },
    },
}
</script>

<style scoped>

    .cursorClick
    {
        cursor: pointer;
    }

    .theme--light.v-application {
        background: #FBF5F2;
    }
    
    .v-application--wrap {
        min-height: 0;
    }
    
</style>