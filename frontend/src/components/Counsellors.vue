<template>
    <div>
        <div class="container-fluid mt-2">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <!-- Card header -->
                        <div class="card-header border-0">
                            <h3 class="mb-0">Counsellor List</h3>
                        </div>
                        <!-- Light table -->
                        <div class="table-responsive">
                            <table class="table align-items-center table-flush">
                                <thead class="thead-light">
                                    <tr>
                                        <th scope="col" class="sort" data-sort="name">Counsellor</th>
                                        <th scope="col" class="sort" data-sort="budget">Practice Number</th>
                                        <th scope="col">Certification</th>
                                        <th scope="col">Status</th>
                                        <th scope="col" class="sort" data-sort="completion">Date Registered</th>
                                        <!-- <th scope="col"></th> -->
                                    </tr>
                                </thead>
                                <tbody class="list" id="patientlist">
                                    <tr 
                                    v-for="item in counsellors" 
                                    :key="item.counsellorID" 
                                    @click="newRedirect('editaccount', item.counsellorID)"
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
                                            {{ item.certification }}
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
            counsellors: 
            [
                // {
                //     counsellorID: 0,
                //     profilePicture: '',
                //     name: 'Sue Lonely',
                //     regNumber: '12daf',
                //     certification: 'Child',
                //     dateRegistered: '12 September 2021',
                //     status: 'Active'
                // },
            ],
            
        }
    },
    
    async mounted() {
        await this.getAllCounsellors()
    },

    methods: {
        async getAllCounsellors()
        {
            const resp = await this.$store.dispatch('users/getAllCounsellors')
            const images = require.context('../assets/img/', false, /\.(png|jpe?g|svg)$/)

            resp.forEach(element => {
                if(element.userInfo.status != "Pending")
                {
                    var temp =
                    {
                        counsellorID: element.userInfo.userID,
                        profilePicture: images(`./${element.userInfo.profilePicture}`),
                        name: element.userInfo.name,
                        regNumber: element.counsellorInfo.practiceNum,
                        certification: element.counsellorInfo.highestCertificate,
                        dateRegistered: moment(element.userInfo.dateRegistered).format('DD MMMM YYYY'),
                        status: element.userInfo.status
                    }
                    this.counsellors.push(temp)
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