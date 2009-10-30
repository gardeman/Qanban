<%@ import="se.qbranch.qanban.Board" %>

<head>

  <meta name='layout' content='inside'/>

<g:javascript library="jquery"/>

<script type="text/javascript" src="/Qanban/js/jquery/jquery.ui.core.js"></script>
<script type="text/javascript" src="/Qanban/js/jquery/jquery.ui.sortable.js"></script>

<jq:jquery>

  var sort = false;

  $('.phase').sortable({
  //connectWith: '.phase',
  //delay: 100,
  start: function(event,ui){
    sort = true;
  },
  update: function(event,ui){
  var newPos = ui.item.prevAll().length;
  var cardId = ui.item.attr('id').split('_')[1];
  $.post('${createLink(controller:'mainView',action:'moveCard')}',{'id': cardId , 'moveTo' : newPos },"json");
  }
  });

  $('.card').click(function(){
    showCard( $(this).attr('id').split('_')[1] );
  });


  function showCard(id){
    if( !sort ){
      //alert('klick on card #' + id);
    }
    sort = false;
  }
  
</jq:jquery>

<style type="text/css">
  .widthForcer { width:${100/board.phases.size()-1}%; margin: 0 0.5%;}
</style>

<title>Qanban</title>

</head>

<body>



  <div id="wrapper">
    <div id="board">

      <g:each var="phase" in="${board.phases}">
        <div class="phaseHolder widthForcer">
          <h3>${phase.name}</h3>
          <ul class="phase" id="phase_${phase.id}">

            <g:each var="card" in="${phase.cards}">

              <li class="card" id="card_${card.id}">${card.title}</li>

            </g:each>

          </ul>

        </div>

      </g:each>

      <div class="leveler"></div>

    </div>

  </div>

</body>
