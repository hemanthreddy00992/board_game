$("#submissionBtn").click(function()
    {
        // Get checked categories
        var checkedCategories = $('#categoryModalBody > div > input:checked')
        var categories = '';
        for (var i = 0; i < checkedCategories.length; i++) {
            categories += checkedCategories[i].value + ',';
        };
        // slice to cut off trailing ","
        var catParam = '&category=' + categories.slice(0, -1);

        // Get checked mechanics
        var checkedMechanics = $('#mechanicModalBody > div > input:checked')
        var mechanics = '';
        for (var i = 0; i < checkedMechanics.length; i++) {
            mechanics += checkedMechanics[i].value + ',';
        };
        // slice to cut off trailing ","
        var mechParam = '&mechanic=' + mechanics.slice(0, -1);

        // Sort param
        var sortParam = '&sort=gameRank';
        // Default to page 1
        var pageParam = "&page=1";
        // Get form parameters
        var values = $("form").serialize();
        // Redirect to url
        window.location.href = "/query?" + values + catParam + mechParam + pageParam + sortParam;
    }
);

$("#addBtn").click(function()
    {
        window.location.href = "/add";
    }
);

function addSlider(min, max, minSelect, sliderId, maxSelect, isInverted) {

    var html5Slider = document.getElementById(sliderId);
    noUiSlider.create(html5Slider, {
        start: [min, max],
        connect: true,
        range: {
            'min': min,
            'max': max
        }
    });


    var minNumber = document.getElementById(minSelect);
    var maxNumber = document.getElementById(maxSelect);

    html5Slider.noUiSlider.on('update', function (values, handle) {
    
        var value = values[handle];
    
        if (handle) {
            maxNumber.value = Math.round(value);
        } else {
            minNumber.value = Math.round(value);
        }
    });

    maxNumber.addEventListener('change', function () {
        if (isInverted)
            html5Slider.noUiSlider.set([Math.round(this.value), null]);
        else
            html5Slider.noUiSlider.set([null, Math.round(this.value)]);
    });
    minNumber.addEventListener('change', function () {
        if (isInverted)
            html5Slider.noUiSlider.set([null, Math.round(this.value)]);
        else
            html5Slider.noUiSlider.set([Math.round(this.value), null]);
    });
    
    


    console.log(minNumber);
    console.log(html5Slider);
    console.log(maxNumber);

};




$(document).ready(function() {


    addSlider(1, 500, 'rankMin', 'rankSlider', 'rankMax', false);
    addSlider(1, 25, 'playersMin', 'playersSlider', 'playersMax', false);
    addSlider(0, 2019, 'yearMin', 'yearSlider', 'yearMax', false);
    addSlider(0, 6000, 'avgPlayTimeMin', 'avgPlayTimeSlider', 'avgPlayTimeMax', false);
    addSlider(0, 6000, 'minPlayTimeMin', 'minPlayTimeSlider', 'minPlayTimeMax', false);
    addSlider(0, 6000, 'maxPlayTimeMin', 'maxPlayTimeSlider', 'maxPlayTimeMax', false);
    addSlider(0, 100000, 'votesMin', 'votesSlider', 'votesMax', false);
    addSlider(0, 100, 'ageMin', 'ageSlider', 'ageMax', false);
    addSlider(0, 6000, 'fansMin', 'fansSlider', 'fansMax', false);
    
            
    
    // Now loads dictionaries from categoryDict.js and mechanicDict.js
    // var categoryDict = {109:"Abstract Strategy", 110:"Action / Dexterity", 111:"Adventure", 112:"Age of Reason", 113:"American Civil War", 114:"American Indian Wars", 115:"American Revolutionary War", 116:"American West", 117:"Ancient", 118:"Animals", 119:"Arabian", 120:"Aviation / Flight", 121:"Bluffing", 122:"Book", 123:"Card Game", 124:"Childrens Game", 125:"City Building", 126:"Civil War", 127:"Civilization", 128:"Collectible Components", 129:"Comic Book / Strip", 130:"Deduction", 131:"Dice", 132:"Economic", 133:"Educational", 134:"Electronic", 135:"Environmental", 136:"Expansion for Base-game", 137:"Exploration", 138:"Fan Expansion", 139:"Fantasy", 140:"Farming", 141:"Fighting", 142:"Game System", 143:"Horror", 144:"Humor", 145:"Industry / Manufacturing", 146:"Korean War", 147:"Mafia", 148:"Math", 149:"Mature / Adult", 150:"Maze", 151:"Medical", 152:"Medieval", 153:"Memory", 154:"Miniatures", 155:"Modern Warfare", 156:"Movies / TV / Radio theme", 157:"Murder/Mystery", 158:"Music", 159:"Mythology", 160:"Napoleonic", 161:"Nautical", 162:"Negotiation", 163:"Novel-based", 164:"Number", 165:"Party Game", 166:"Pike and Shot", 167:"Pirates", 168:"Political", 169:"Post-Napoleonic", 170:"Prehistoric", 171:"Print & Play", 172:"Puzzle", 173:"Racing", 174:"Real-time", 175:"Religious", 176:"Renaissance", 177:"Science Fiction", 178:"Space Exploration", 179:"Spies/Secret Agents", 180:"Sports", 181:"Territory Building", 182:"Trains", 183:"Transportation", 184:"Travel", 185:"Trivia", 186:"Video Game Theme", 187:"Vietnam War", 188:"Wargame", 189:"Word Game", 190:"World War I", 191:"World War II", 192:"Zombies"};
    // var mechanicDict = {1:"Acting", 2:"Action / Movement Programming", 3:"Action Point Allowance System", 4:"Area Control / Area Influence", 5:"Area Enclosure", 6:"Area Movement", 7:"Area-Impulse", 8:"Auction/Bidding", 9:"Betting/Wagering", 10:"Campaign / Battle Card Driven", 11:"Card Drafting", 12:"Chit-Pull System", 13:"Commodity Speculation", 14:"Cooperative Play", 15:"Crayon Rail System", 16:"Deck / Pool Building", 17:"Dice Rolling", 18:"Grid Movement", 19:"Hand Management", 20:"Hex-and-Counter", 21:"Hidden Traitor", 22:"Line Drawing", 23:"Memory", 24:"Modular Board", 25:"Paper-and-Pencil", 26:"Partnerships", 27:"Pattern Building", 28:"Pattern Recognition", 29:"Pick-up and Deliver", 30:"Player Elimination", 31:"Point to Point Movement", 32:"Press Your Luck", 33:"Rock-Paper-Scissors", 34:"Role Playing", 35:"Roll / Spin and Move", 36:"Rondel", 37:"Route/Network Building", 38:"Secret Unit Deployment", 39:"Set Collection", 40:"Simulation", 41:"Simultaneous Action Selection", 42:"Singing", 43:"Stock Holding", 44:"Storytelling", 45:"Take That", 46:"Tile Placement", 47:"Time Track", 48:"Trading", 49:"Trick-taking", 50:"Variable Phase Order", 51:"Variable Player Powers", 52:"Voting", 53:"Worker Placement"};
    

    // Generate category checkboxes
    var addedCategoriesRow = '';
    for (var key in categoryDict) {
        var catName = categoryDict[key];
        addedCategoriesRow += '<div class="form-check form-check-inline"><input class="form-check-input" type="checkbox" id="'+catName+'" value="'+key+'"><label class="form-check-label modal-label" for="'+catName+'">'+catName+'</label></div>';
    }
    $('#categoryModalBody').html(addedCategoriesRow);

    // Generate mechanic checkboxes
    var addedMechanicsRow = '';
    for (var key in mechanicDict) {
        var mechName = mechanicDict[key];
        addedMechanicsRow += '<div class="form-check form-check-inline"><input class="form-check-input" type="checkbox" id="'+mechName+'" value="'+key+'"><label class="form-check-label modal-label" for="'+mechName+'">'+mechName+'</label></div>';
    }
    $('#mechanicModalBody').html(addedMechanicsRow);
    
 });