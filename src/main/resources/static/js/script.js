// WorkSpace Intranet — script.js

// ── 사이드바 현재 페이지 active 표시 ──
document.addEventListener('DOMContentLoaded', () => {
  const path = window.location.pathname;
  document.querySelectorAll('.nav-item').forEach(item => {
    const href = item.getAttribute('href');
    if (href && path.startsWith(href) && href !== '/') {
      item.classList.add('active');
    } else if (href === '/' && path === '/') {
      item.classList.add('active');
    }
  });
});

// ── 파일 드래그&드롭 ──
document.querySelectorAll('.upload-zone').forEach(zone => {
  zone.addEventListener('dragover', e => {
    e.preventDefault();
    zone.style.borderColor = 'var(--accent)';
    zone.style.background = 'var(--primary-pale)';
  });
  zone.addEventListener('dragleave', () => {
    zone.style.borderColor = '';
    zone.style.background = '';
  });
  zone.addEventListener('drop', e => {
    e.preventDefault();
    zone.style.borderColor = '';
    zone.style.background = '';
    const input = zone.querySelector('input[type="file"]');
    if (input) {
      input.files = e.dataTransfer.files;
      input.dispatchEvent(new Event('change'));
    }
  });
});
function toggleMsgPopup(e) {
    e.stopPropagation();
    document.getElementById('msgPopup').classList.toggle('open');
  }

  // 팝업 바깥 클릭 시 닫기
  document.addEventListener('click', function(e) {
    var wrap = document.getElementById('msgPopupWrap');
    if (wrap && !wrap.contains(e.target)) {
      document.getElementById('msgPopup').classList.remove('open');
    }
  });